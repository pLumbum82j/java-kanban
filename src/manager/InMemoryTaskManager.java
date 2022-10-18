package manager;

import manager.history.HistoryManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;


public class InMemoryTaskManager implements TaskManager {

    private int generatorId = 1;

    /**
     * "Пункт 1 - Хранение данных Task/Epic/Subtask в HashMap"
     */
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private final HistoryManager defaultHistory = Managers.getDefault.History();

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    /**
     * "Пункт 2.1 - Получение списка Task"
     */
    @Override
    public ArrayList<Task> getTask() {
        return new ArrayList<>(tasks.values());
    }

    /**
     * "Пункт 2.1 - Получение списка EpicTask"
     */
    @Override
    public ArrayList<Task> getEpicTask() {
        return new ArrayList<>(epics.values());
    }

    /**
     * "Пункт 2.1 - Получение списка SubTask"
     */
    @Override
    public ArrayList<Task> getSubTask() {
        return new ArrayList<>(subtasks.values());
    }

    /**
     * "Пункт 2.2 - Удаление всех списков задач"
     */
    @Override
    public void clearAll() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    /**
     * "Пункт 2.2 - Удаление всех списка Task"
     */
    @Override
    public void clearTask() {
        tasks.clear();
    }

    /**
     * "Пункт 2.2 - Удаление всех списка EpicTask и SubTask"
     */
    @Override
    public void clearEpic() {
        epics.clear();
        subtasks.clear();
    }

    /**
     * "Пункт 2.2 - Удаление всех списка SubTask"
     */
    @Override
    public void clearSubTask() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubTaskId();
            updateEpicStatus(epic);
        }

    }

    /**
     * "Пункт 2.3 - Получение определённого Task по ID"
     */
    @Override
    public Task getTaskId(int id) {
        return tasks.get(id);
    }

    /**
     * "Пункт 2.3 - Получение определённого EpicTask по ID"
     */
    @Override
    public Task getEpicId(int id) {
        return epics.get(id);
    }

    /**
     * "Пункт 2.3 - Получение определённого SubTask по ID"
     */
    @Override
    public Task getSubTaskId(int id) {
        return subtasks.get(id);
    }

    /**
     * "Пункт 2.4 - Создание Task"
     */
    @Override
    public void addTask(Task task) {
        task.setId(generatorId);
        tasks.put(task.getId(), task);
        generatorId++;
    }

    /**
     * "Пункт 2.4 - Создание EpicTask"
     */
    @Override
    public void addEpicTask(Epic epic) {
        epic.setId(generatorId);
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic);
        generatorId++;
    }

    /**
     * "Пункт 2.4 - Создание SubTask"
     */
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

    /**
     * "Пункт 2.5 - Изменение определенного Task по ID"
     */
    @Override
    public void changeTask(Task task) {
        if (tasks.get(task.getId()) != null) {
            tasks.put(task.getId(), task);
        }
    }

    /**
     * "Пункт 2.5 - Изменение определенного Epic по ID"
     */
    @Override
    public void changeEpic(Epic epic) {
        if (epics.get(epic.getId()) != null) {
            epics.put(epic.getId(), epic);
        }
    }

    /**
     * "Пункт 2.5 - Изменение определенного Subtask по ID"
     */
    @Override
    public void changeSubTask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        if (subtasks.get(subtask.getId()) != null) {
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(epicId);
            updateEpicStatus(epic);
        }
    }

    /**
     * "Пункт 2.6 - Удаление определенного Task по ID"
     */
    @Override
    public void delTask(int id) {
        tasks.remove(id);
    }

    /**
     * "Пункт 2.6 - Удаление определенного EpicTask по ID"
     */
    @Override
    public void delEpic(int id) {
        Epic deletedEpic = epics.remove(id);
        for (Integer subtaskId : deletedEpic.getSubtaskListId()) {
            subtasks.remove(subtaskId);
        }
    }

    /**
     * "Пункт 2.6 - Удаление определенного SubTask по ID"
     */
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


    /**
     * "Пункт 3.1 - Получение списка всех подзадач определённого эпика"
     */
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

    /**
     * "Пункт 4.2 - Определение статуса EpicTask при создании или изменении SubTask"
     */
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
