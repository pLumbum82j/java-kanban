package manager;

import manager.history.HistoryManager;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class InMemoryTaskManager implements TaskManager {

    protected int generatorId = 1;

    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();


    protected HistoryManager defaultHistory = Managers.getDefaultHistory();

    //    protected final Comparator<Task> taskComparator = Comparator.comparing(task -> {
 //       if(task.getStartTime().isEmpty()) return null;
//        else return task.getStartTime().get();});
//
//    protected Set<Task> prioritizedTasks = new TreeSet<>(taskComparator);
    protected final Set<Task> prioritizedTasks = new TreeSet<>(new Comparator<Task>() {
        @Override
        public int compare(Task o1, Task o2) {
            Optional<LocalDateTime> t1 = o1.getStartTime();
            Optional<LocalDateTime> t2 = o2.getStartTime();

            if (t1.isPresent() && t2.isPresent()) {
                return t1.get().compareTo(t2.get());
            } else if (t1.isPresent()) {
                return -1;
            } else if (t2.isPresent()) {
                return 1;
            }
            return 0;
        }
    }.thenComparing(new PersonNameComparator()));

    class PersonNameComparator implements Comparator<Task> {

        public int compare(Task a, Task b) {

            return a.getName().compareTo(b.getName());
        }
    }

    @Override
    public List<Task> getHistory() {
        return defaultHistory.getHistory();
    }

    @Override
    public ArrayList<Task> getTask() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Task> getEpicTask() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Task> getSubTask() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void clearAll() {
        clearTask();
        clearEpic();
    }

    @Override
    public void clearTask() {
        for (Task task : tasks.values()) {
            defaultHistory.remove(task.getId());
        }
        tasks.clear();
    }

    @Override
    public void clearEpic() {
        for (Task subtask : subtasks.values()) {
            defaultHistory.remove(subtask.getId());
        }
        for (Task epic : epics.values()) {
            defaultHistory.remove(epic.getId());
        }
        subtasks.clear();
        epics.clear();

    }

    @Override
    public void clearSubTask() {
        for (Task subtask : subtasks.values()) {
            defaultHistory.remove(subtask.getId());
        }
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubTaskId();
            updateEpicStatus(epic);
        }

    }

    @Override
    public Task getTaskById(int id) {
        defaultHistory.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Task getEpicById(int id) {
        defaultHistory.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Task getSubTaskById(int id) {
        defaultHistory.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public void addTask(Task task) {
        prioritizedTasks.add(task);
        validateTaskPriority();
        task.setId(generatorId);
        tasks.put(task.getId(), task);
        generatorId++;
    }

    @Override
    public void addEpicTask(Epic epic) {
        epic.setId(generatorId);
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic);
        generatorId++;
    }

    @Override
    public void addSubTask(Subtask subtask) {
        prioritizedTasks.add(subtask);
        subtask.setId(generatorId);
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }
        subtask.setId(generatorId);
        subtasks.put(subtask.getId(), subtask);
        epic.addSubTaskId(subtask.getId());
        updateEpicStatus(epic);
        updateTimeEpic(epic);
        generatorId++;
    }

    @Override
    public void changeTask(Task task) {
        if (tasks.get(task.getId()) != null) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void changeEpic(Epic epic) {
        if (epics.get(epic.getId()) != null) {
            epics.put(epic.getId(), epic);
        }
    }

    @Override
    public void changeSubTask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        if (subtasks.get(subtask.getId()) != null) {
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(epicId);
            updateEpicStatus(epic);
            updateTimeEpic(epic);
        }
    }

    @Override
    public void delTask(int id) {
        tasks.remove(id);
        defaultHistory.remove(id);
    }

    @Override
    public void delEpic(int id) {
        Epic deletedEpic = epics.remove(id);
        for (Integer subtaskId : deletedEpic.getSubtaskListId()) {
            subtasks.remove(subtaskId);
            defaultHistory.remove(subtaskId);
        }
        defaultHistory.remove(id);
    }

    @Override
    public void delSubTask(int id) {
        Subtask deletedSubtask = subtasks.remove(id);
        if (deletedSubtask != null) {
            int deleteEpicId = deletedSubtask.getEpicId();
            Epic deletedEpic = epics.get(deleteEpicId);
            deletedEpic.removeSubTaskId(id);
            updateEpicStatus(deletedEpic);
            updateTimeEpic(deletedEpic);
        }
        defaultHistory.remove(id);
    }

    @Override
    public ArrayList<Subtask> getSubtaskListEpics(int id) {
        ArrayList<Subtask> resultSubtasklist = new ArrayList<>();
        for (Subtask list : subtasks.values()) {
            if (list.getEpicId() == id) {
                resultSubtasklist.add(list);
            }
        }
        return resultSubtasklist;
    }

    @Override
    public void updateEpicStatus(Epic epic) {
        ArrayList<Integer> subtaskListId = epic.getSubtaskListId();
        if (subtaskListId.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        Status status = null;
        for (int subtaskId : subtaskListId) {
            Subtask subtask = subtasks.get(subtaskId);

            if (status == null) {
                status = subtask.getStatus();
                continue;
            }
            if (status.equals(subtask.getStatus())) {
                continue;
            }
            epic.setStatus(Status.IN_PROGRESS);
            return;
        }
        epic.setStatus(status);
    }

    /**
     * "Метод добавления ID Epic'a в subtask"
     *
     * @param subTask Subtask
     */
    protected void addEpicIdForSubtask(Subtask subTask) {
        int epicId = subTask.getEpicId();
        Epic epic = epics.get(epicId);

        if (epic == null) {
            return;
        }

        epic.addSubTaskId(subTask.getId());
        updateEpicStatus(epic);
    }

    public void updateTimeEpic(Epic epic) {
        LocalDateTime mainStartTime = null;
        LocalDateTime endTime = null;
        long duration = 0;
        for (Subtask subtask : getSubtaskListEpics(epic.getId())) {
            endTime = subtask.getEndTime();
            Optional<LocalDateTime> startTime = subtask.getStartTime();
            if (startTime.isPresent()) {
                mainStartTime = mainStartTime == null ? startTime.get() : mainStartTime;
                if (startTime.get().isBefore(mainStartTime)) {
                    mainStartTime = startTime.get();
                }
                if (subtask.getEndTime().isAfter(endTime)) {
                    endTime = subtask.getEndTime();
                }
                duration += subtask.getDuration();
            }
        }
        // if(mainStartTime!=null) {
        epic.setStartTime(mainStartTime);
        // }
        epic.setEndTime(endTime);
        epic.setDuration(duration);
    }


        public boolean checkTime(Task task) {
        List<Task> tasks = List.copyOf(prioritizedTasks);
        int sizeTimeNull = 0;
        if (tasks.size() > 0) {
            for (Task taskSave : tasks) {
                if (task.getStartTime().isEmpty()) {
                    return false;
                }else {
                    if (taskSave.getStartTime().isPresent() && taskSave.getEndTime() != null) {
                        if (task.getStartTime().get().isBefore(taskSave.getStartTime().get())
                                && task.getEndTime().isBefore(taskSave.getStartTime().get())) {
                            return true;
                        } else if (task.getStartTime().get().isAfter(taskSave.getEndTime())
                                && task.getEndTime().isAfter(taskSave.getEndTime())) {
                            return true;
                        }
                    } else {
                        sizeTimeNull++;
                    }
                }
            }
            return sizeTimeNull == tasks.size();
        } else {
            return true;
        }
    }

    private void validateTaskPriority() {
        List<Task> tasks = getPrioritizedTasks();

        for (int i = 1; i < tasks.size(); i++) {
            Task task = tasks.get(i);

            boolean taskHasIntersections = checkTime(task);

            if (taskHasIntersections) {
               // throw new ManagerValidateException(
                System.out.println("Задачи #" + task.getId() + " и #" + tasks.get(i - 1) + "пересекаются");
            }
        }
    }


    private List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }


    @Override
    public List<Task> getAllTasks() {
        if (tasks.size() == 0) {
            System.out.println("Task list is empty");
            return Collections.emptyList();
        }
        return new ArrayList<>(tasks.values());
    }

}
