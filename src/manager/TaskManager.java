package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;

interface TaskManager{

    ArrayList<Task> getTasks();

    /**
     * "Пункт 2.1 - Получение списка Task"
     */
    public ArrayList<Task> getTask();

    /**
     * "Пункт 2.1 - Получение списка EpicTask"
     */
    public ArrayList<Task> getEpicTask();

    /**
     * "Пункт 2.1 - Получение списка SubTask"
     */
    public ArrayList<Task> getSubTask();

    /**
     * "Пункт 2.2 - Удаление всех списков задач"
     */
    public void clearAll();

    /**
     * "Пункт 2.2 - Удаление всех списка Task"
     */
    public void clearTask();

    /**
     * "Пункт 2.2 - Удаление всех списка EpicTask и SubTask"
     */
    public void clearEpic();

    /**
     * "Пункт 2.2 - Удаление всех списка SubTask"
     */
    public void clearSubTask();
    /**
     * "Пункт 2.3 - Получение определённого Task по ID"
     */
    public Task getTaskId(int id);

    /**
     * "Пункт 2.3 - Получение определённого EpicTask по ID"
     */
    public Task getEpicId(int id);

    /**
     * "Пункт 2.3 - Получение определённого SubTask по ID"
     */
    public Task getSubTaskId(int id);

    /**
     * "Пункт 2.4 - Создание Task"
     */
    public void addTask(Task task);

    /**
     * "Пункт 2.4 - Создание EpicTask"
     */
    public void addEpicTask(Epic epic);

    /**
     * "Пункт 2.4 - Создание SubTask"
     */
    public void addSubTask(Subtask subtask);

    /**
     * "Пункт 2.5 - Изменение определенного Task по ID"
     */
    public void changeTask(Task task);

    /**
     * "Пункт 2.5 - Изменение определенного Epic по ID"
     */
    public void changeEpic(Epic epic);

    /**
     * "Пункт 2.5 - Изменение определенного Subtask по ID"
     */
    public void changeSubTask(Subtask subtask);

    /**
     * "Пункт 2.6 - Удаление определенного Task по ID"
     */
    public void delTask(int id);

    /**
     * "Пункт 2.6 - Удаление определенного EpicTask по ID"
     */
    public void delEpic(int id);

    /**
     * "Пункт 2.6 - Удаление определенного SubTask по ID"
     */
    public void delSubTask(int id);


    /**
     * "Пункт 3.1 - Получение списка всех подзадач определённого эпика"
     */
    public ArrayList<Subtask> getSubtaskListEpics(int id);

    /**
     * "Пункт 4.2 - Определение статуса EpicTask при создании или изменении SubTask"
     */
    void updateEpicStatus(Epic epic);
}
