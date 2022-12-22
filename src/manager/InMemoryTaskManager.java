package manager;

import exceprion.ManagerValidateException;
import manager.history.HistoryManager;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.time.LocalDateTime;
import java.util.*;


public class InMemoryTaskManager implements TaskManager {

    protected int generatorId = 1;

    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected HistoryManager defaultHistory = Managers.getDefaultHistory();
    protected final Comparator<Task> taskComparator = (t1, t2) -> {
        if (t1.getStartTime().isEmpty() && t2.getStartTime().isEmpty()) {
            return t2.getId() - t1.getId();
        }
        if (t1.getStartTime().isEmpty()) {
            return 1;
        }
        if (t2.getStartTime().isEmpty()) {
            return -1;
        }
        if (t1.getStartTime().equals(t2.getStartTime())) {
            return t2.getId() - t1.getId();
        }
        return t1.getStartTime().get().compareTo(t2.getEndTime());
    };

    protected Set<Task> prioritizedTasks = new TreeSet<>(taskComparator);

    @Override
    public List<Task> getHistory() {
        return defaultHistory.getHistory();
    }

    @Override
    public List<Task> getTask() {
        if (tasks.size() == 0) {
            System.out.println("Список Task's - пуст");
            return Collections.emptyList();
        }
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Task> getEpicTask() {
        if (epics.size() == 0) {
            System.out.println("Список Epic's - пуст");
            return Collections.emptyList();
        }
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Task> getSubTask() {
        if (subtasks.size() == 0) {
            System.out.println("Список SubTask's - пуст");
            return Collections.emptyList();
        }
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
    public Task addTask(Task task) {
        if (task == null) {
            return null;
        }
        if (validateTaskPriority(task)) {
            throw new ManagerValidateException(
                    "Задача #" + task.getName() + " имеет пересечение!");
        }
        prioritizedTasks.add(task);
        task.setId(generatorId);
        tasks.put(task.getId(), task);
        generatorId++;
        return task;
    }

    @Override
    public Epic addEpicTask(Epic epic) {
        if (epic == null) {
            return null;
        }
        epic.setId(generatorId);
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic);
        generatorId++;
        return epic;
    }

    @Override
    public Subtask addSubTask(Subtask subtask) {
        if (subtask == null) {
            return null;
        }
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return null;
        }
        if (validateTaskPriority(subtask)) {
            throw new ManagerValidateException(
                    "Задача #" + subtask.getName() + " имеет пересечение!");
        }
        prioritizedTasks.add(subtask);
        subtask.setId(generatorId);
        subtasks.put(subtask.getId(), subtask);
        epic.addSubTaskId(subtask.getId());
        updateEpicStatus(epic);
        updateTimeEpic(epic);
        generatorId++;
        return subtask;
    }

    @Override
    public void changeTask(Task newTask) {
        Task oldTask = tasks.get(newTask.getId());
        if (oldTask != null) {
            prioritizedTasks.remove(oldTask);
            tasks.put(newTask.getId(), newTask);
            updateTaskStatus(newTask);
        }
    }

    @Override
    public void changeEpic(Epic newEpic) {
        if (epics.get(newEpic.getId()) != null) {
            newEpic.setSubtaskListId(epics.get(newEpic.getId()).getSubtaskListId());
            epics.put(newEpic.getId(), newEpic);
            updateEpicStatus(newEpic);
            updateTimeEpic(newEpic);
        }
    }

    @Override
    public void changeSubTask(Subtask newSubtask) {
        Subtask oldSubtask = subtasks.get(newSubtask.getId());
        int epicId = newSubtask.getEpicId();
        if (subtasks.get(newSubtask.getId()) != null) {
            prioritizedTasks.remove(oldSubtask);
            subtasks.put(newSubtask.getId(), newSubtask);
            updateSubtaskStatus(newSubtask);
            Epic epic = epics.get(epicId);
            updateEpicStatus(epic);
            updateTimeEpic(epic);
        }
    }

    @Override
    public void delTask(int id) {
        defaultHistory.remove(id);
        prioritizedTasks.remove(tasks.get(id));
        tasks.remove(id);
    }

    @Override
    public void delEpic(int id) {
        prioritizedTasks.remove(epics.get(id));
        ArrayList<Integer> subtaskList = epics.remove(id).getSubtaskListId();
        for (Integer subtaskId : subtaskList) {
            prioritizedTasks.remove(subtasks.get(subtaskId));
            subtasks.remove(subtaskId);
            defaultHistory.remove(subtaskId);
        }
        defaultHistory.remove(id);
    }

    @Override
    public void delSubTask(int id) {
        prioritizedTasks.remove(subtasks.get(id));
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
        if (epic != null && epics.containsKey(epic.getId())) {
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
        } else {
            System.out.println("Task не найден");
        }

    }

    @Override
    public void updateTaskStatus(Task task) {
        if (task != null && tasks.containsKey(task.getId())) {
            validateTaskPriority(task);
            prioritizedTasks.add(task);
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Task не найден");
        }
    }

    @Override
    public void updateSubtaskStatus(Subtask subtask) {
        if (subtask != null && subtasks.containsKey(subtask.getId())) {
            validateTaskPriority(subtask);
            prioritizedTasks.add(subtask);
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpicId());
            updateEpicStatus(epic);
            updateTimeEpic(epic);
        } else {
            System.out.println("SubTask не найден");
        }
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

    /**
     * "Метод поиска продолжительности и времени окончания Epic'a"
     *
     * @param epic Epic
     */
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
        epic.setStartTime(mainStartTime);
        epic.setEndTime(endTime);
        epic.setDuration(duration);
    }

    /**
     * "Метод проверки задач на пересечение по времени"
     *
     * @param task Поступившая задача
     * @return Флаг выброса ManagerValidateException
     */
    private boolean validateTaskPriority(Task task) {
        List<Task> tasks = getPrioritizedTasks();
        if (tasks.size() == 0) {
            return false;
        }

        for (Task taskSave : tasks) {
            if (task.getStartTime().isEmpty()) {
                System.out.println("Передаваемая задача " + task.getName() + " не имеет время старта");
                return false;
            } else {
                if (taskSave.getStartTime().isPresent() && taskSave.getEndTime() != null) {
                    if (task.getStartTime().get().isAfter(taskSave.getStartTime().get())
                            && task.getStartTime().get().isBefore(taskSave.getEndTime())) {
                        System.out.println("Пересечение: " + task.getName());
                        return true;
                    } else if (task.getEndTime().isAfter(taskSave.getStartTime().get())
                            && task.getEndTime().isBefore(taskSave.getEndTime())) {
                        System.out.println("Пересечение: " + task.getName());
                        return true;
                    } else if (task.getStartTime().get().isBefore(taskSave.getStartTime().get())
                            && task.getEndTime().isAfter(taskSave.getEndTime())) {
                        System.out.println("Пересечение: " + task.getName());
                        return true;
                    } else if (task.getStartTime().get().isAfter(taskSave.getStartTime().get())
                            && task.getEndTime().isBefore(taskSave.getEndTime())) {
                        System.out.println("Пересечение: " + task.getName());
                        return true;
                    } else if (task.getStartTime().get().isEqual(taskSave.getStartTime().get())) {
                        System.out.println("Пересечение: " + task.getName());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * "Метод возвращает список задач с приоритетом по времени"
     *
     * @return Список отсортированных задач
     */
    private List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }
}
