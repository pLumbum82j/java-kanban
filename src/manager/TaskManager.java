package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    List<Task> getHistory();

    ArrayList<Task> getTasks();

    /**
     * "Пункт 2.1 - Получение списка Task"
     */
    ArrayList<Task> getTask();

    /**
     * "Пункт 2.1 - Получение списка EpicTask"
     */
    ArrayList<Task> getEpicTask();

    /**
     * "Пункт 2.1 - Получение списка SubTask"
     */
    ArrayList<Task> getSubTask();

    /**
     * "Пункт 2.2 - Удаление всех списков задач"
     */
    void clearAll();

    /**
     * "Пункт 2.2 - Удаление всех списка Task"
     */
    void clearTask();

    /**
     * "Пункт 2.2 - Удаление всех списка EpicTask и SubTask"
     */
    void clearEpic();

    /**
     * "Пункт 2.2 - Удаление всех списка SubTask"
     */
    void clearSubTask();

    /**
     * "Пункт 2.3 - Получение определённого Task по ID"
     */
    Task getTaskId(int id);

    /**
     * "Пункт 2.3 - Получение определённого EpicTask по ID"
     */
    Task getEpicId(int id);

    /**
     * "Пункт 2.3 - Получение определённого SubTask по ID"
     */
    Task getSubTaskId(int id);

    /**
     * "Пункт 2.4 - Создание Task"
     */
    void addTask(Task task);

    /**
     * "Пункт 2.4 - Создание EpicTask"
     */
    void addEpicTask(Epic epic);

    /**
     * "Пункт 2.4 - Создание SubTask"
     */
    void addSubTask(Subtask subtask);

    /**
     * "Пункт 2.5 - Изменение определенного Task по ID"
     */
    void changeTask(Task task);

    /**
     * "Пункт 2.5 - Изменение определенного Epic по ID"
     */
    void changeEpic(Epic epic);

    /**
     * "Пункт 2.5 - Изменение определенного Subtask по ID"
     */
    void changeSubTask(Subtask subtask);

    /**
     * "Пункт 2.6 - Удаление определенного Task по ID"
     */
    void delTask(int id);

    /**
     * "Пункт 2.6 - Удаление определенного EpicTask по ID"
     */
    void delEpic(int id);

    /**
     * "Пункт 2.6 - Удаление определенного SubTask по ID"
     */
    void delSubTask(int id);


    /**
     * "Пункт 3.1 - Получение списка всех подзадач определённого эпика"
     */
    ArrayList<Subtask> getSubtaskListEpics(int id);

    /**
     * "Пункт 4.2 - Определение статуса EpicTask при создании или изменении SubTask"
     */
    void updateEpicStatus(Epic epic);
}
