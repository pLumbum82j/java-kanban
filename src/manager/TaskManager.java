package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    /**
     * "Спринт 4 - Получаем список history через класс Manager и метод getDefaultHistory()"
     */
    List<Task> getHistory();

    /**
     * "Спринт 3 - Пункт 2.1 - Получение списка Task"
     */
    ArrayList<Task> getTask();

    /**
     * "Спринт 3 - Пункт 2.1 - Получение списка EpicTask"
     */
    ArrayList<Task> getEpicTask();

    /**
     * "Спринт 3 - Пункт 2.1 - Получение списка SubTask"
     */
    ArrayList<Task> getSubTask();

    /**
     * "Спринт 3 - Пункт 2.2 - Удаление всех списков задач"
     */
    void clearAll();

    /**
     * "Спринт 3 - Пункт 2.2 - Удаление всех списка Task"
     */
    void clearTask();

    /**
     * "Спринт 3 - Пункт 2.2 - Удаление всех списка EpicTask и SubTask"
     */
    void clearEpic();

    /**
     * "Спринт 3 - Пункт 2.2 - Удаление всех списка SubTask"
     */
    void clearSubTask();

    /**
     * "Спринт 3 - Пункт 2.3 - Получение определённого Task по ID"
     */
    Task getTaskId(int id);

    /**
     * "Спринт 3 - Пункт 2.3 - Получение определённого EpicTask по ID"
     */
    Task getEpicId(int id);

    /**
     * "Спринт 3 - Пункт 2.3 - Получение определённого SubTask по ID"
     */
    Task getSubTaskId(int id);

    /**
     * "Спринт 3 - Пункт 2.4 - Создание Task"
     */
    void addTask(Task task);

    /**
     * "Спринт 3 - Пункт 2.4 - Создание EpicTask"
     */
    void addEpicTask(Epic epic);

    /**
     * "Спринт 3 - Пункт 2.4 - Создание SubTask"
     */
    void addSubTask(Subtask subtask);

    /**
     * "Спринт 3 - Пункт 2.5 - Изменение определенного Task по ID"
     */
    void changeTask(Task task);

    /**
     * "Спринт 3 - Пункт 2.5 - Изменение определенного Epic по ID"
     */
    void changeEpic(Epic epic);

    /**
     * "Спринт 3 - Пункт 2.5 - Изменение определенного Subtask по ID"
     */
    void changeSubTask(Subtask subtask);

    /**
     * "Спринт 3 - Пункт 2.6 - Удаление определенного Task по ID"
     */
    void delTask(int id);

    /**
     * "Спринт 3 - Пункт 2.6 - Удаление определенного EpicTask по ID"
     */
    void delEpic(int id);

    /**
     * "Спринт 3 - Пункт 2.6 - Удаление определенного SubTask по ID"
     */
    void delSubTask(int id);


    /**
     * "Спринт 3 - Пункт 3.1 - Получение списка всех подзадач определённого эпика"
     */
    ArrayList<Subtask> getSubtaskListEpics(int id);

    /**
     * "Спринт 3 - Пункт 4.2 - Определение статуса EpicTask при создании или изменении SubTask"
     */
    void updateEpicStatus(Epic epic);
}
