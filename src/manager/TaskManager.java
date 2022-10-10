package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private int generatorId = 1;

    /**
     * "Пункт 1 - Хранение данных Task/Epic/Subtask в HashMap"
     */
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();


    /**
     * "Пункт 2.1 - Получение списка Task"
     */
    public ArrayList<Task> getTask() {
        return new ArrayList<>(tasks.values());
    }

    /**
     * "Пункт 2.1 - Получение списка EpicTask"
     */
    public ArrayList<Task> getEpicTask() {
        return new ArrayList<>(epics.values());
    }

    /**
     * "Пункт 2.1 - Получение списка SubTask"
     */
    public ArrayList<Task> getSubTask() {
        return new ArrayList<>(subtasks.values());
    }

    /**
     * "Пункт 2.2 - Удаление всех списков задач"
     */
    public void clearAll() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    /**
     * "Пункт 2.2 - Удаление всех списка Task"
     */
    public void clearTask() {
        tasks.clear();
    }

    /**
     * "Пункт 2.2 - Удаление всех списка EpicTask и SubTask"
     */
    public void clearEpic() {
        epics.clear();
        subtasks.clear();
    }

    /**
     * "Пункт 2.2 - Удаление всех списка SubTask"
     */
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
    public Task getTaskId(int id) {
        return tasks.get(id);
    }

    /**
     * "Пункт 2.3 - Получение определённого EpicTask по ID"
     */
    public Task getEpicId(int id) {
        return epics.get(id);
    }

    /**
     * "Пункт 2.3 - Получение определённого SubTask по ID"
     */
    public Task getSubTaskId(int id) {
        return subtasks.get(id);
    }

    /**
     * "Пункт 2.4 - Создание Task"
     */
    public void addTask(Task task) {
        task.setId(generatorId);
        tasks.put(task.getId(), task);
        generatorId++;
    }

    /**
     * "Пункт 2.4 - Создание EpicTask"
     */
    public void addEpicTask(Epic epic) {
        epic.setId(generatorId);
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic);
        generatorId++;
    }

    /**
     * "Пункт 2.4 - Создание SubTask"
     */
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
    public void changeTask(Task task) {
        if (tasks.get(task.getId()) != null) {
            tasks.put(task.getId(), task);
        }
    }

    /**
     * "Пункт 2.5 - Изменение определенного Epic по ID"
     */
    public void changeEpic(Epic epic) {
        if (epics.get(epic.getId()) != null) {
            epics.put(epic.getId(), epic);
        }
    }

    /**
     * "Пункт 2.5 - Изменение определенного Subtask по ID"
     */
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
    public void delTask(int id) {
        tasks.remove(id);
    }

    /**
     * "Пункт 2.6 - Удаление определенного EpicTask по ID"
     */
    public void delEpic(int id) {
        Epic deletedEpic = epics.remove(id);
        for (Integer subtaskId : deletedEpic.getSubtaskListId()) {
            subtasks.remove(subtaskId);
        }
    }

    /**
     * "Пункт 2.6 - Удаление определенного SubTask по ID <<<<< Доделать, нужен пересчёт updateEpicStatus(epic)"
     */
    public void delSubTask(int id) {
        Subtask deletedSubtask = subtasks.remove(id);
        if (deletedSubtask != null) {
            int deleteEpicId = deletedSubtask.getEpicId();
            Epic deletedEpic = epics.remove(deleteEpicId);
            deletedEpic.getSubtaskListId().remove(Integer.valueOf(id));
            updateEpicStatus(deletedEpic);
        }
    }

    /**
     * "Пункт 3.1 - Получение списка всех подзадач определённого эпика"
     */
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
    private void updateEpicStatus(Epic epic) {
        ArrayList<Integer> subtaskListId = epic.getSubtaskListId();
        if (subtaskListId.isEmpty()) {
            epic.setStatus("NEW");
            return;
        }
        String status = null;
        for (int subtaskId : subtaskListId) {
            Subtask subtask = subtasks.get(subtaskId);

            if (status == null) {
                status = subtask.getStatus();
                continue;
            }
            if (status.equals(subtask.getStatus())) {
                continue;
            }
            epic.setStatus("IN PROGRESS");
            return;
        }
        epic.setStatus(status);
    }
}
