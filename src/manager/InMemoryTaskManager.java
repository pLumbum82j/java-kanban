package manager;

import manager.history.HistoryManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InMemoryTaskManager implements TaskManager {

    private int generatorId = 1;

    /**
     * "Пункт 1 - Хранение данных Task/Epic/Subtask в HashMap"
     */
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private HistoryManager defaultHistory = Managers.getDefaultHistory();

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
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void clearTask() {
        tasks.clear();
    }

    @Override
    public void clearEpic() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void clearSubTask() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubTaskId();
            updateEpicStatus(epic);
        }

    }

    @Override
    public Task getTaskId(int id) {
        defaultHistory.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Task getEpicId(int id) {
        defaultHistory.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Task getSubTaskId(int id) {
        defaultHistory.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public void addTask(Task task) {
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
        }
    }

    @Override
    public void delTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void delEpic(int id) {
        Epic deletedEpic = epics.remove(id);
        for (Integer subtaskId : deletedEpic.getSubtaskListId()) {
            subtasks.remove(subtaskId);
        }
    }

    @Override
    public void delSubTask(int id) {
        Subtask deletedSubtask = subtasks.remove(id);
        if (deletedSubtask != null) {
            int deleteEpicId = deletedSubtask.getEpicId();
            Epic deletedEpic = epics.get(deleteEpicId);
            deletedEpic.removeSubTaskId(id);
            updateEpicStatus(deletedEpic);
        }
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

}