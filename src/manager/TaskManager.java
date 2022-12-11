package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * "Интерфейс открытых методов для использования любыми Task менеджерами"
 */
public interface TaskManager {

    /**
     * "Метод получения списка history"
     *
     * @return список последних опрошенных задач
     */
    List<Task> getHistory();

    /**
     * "Метод получения списка Task"
     *
     * @return список Task
     */
    ArrayList<Task> getTask();

    /**
     * "Метод прлучения списка EpicTask"
     *
     * @return список EpicTask
     */
    ArrayList<Task> getEpicTask();

    /**
     * "Метод получения списка SubTask"
     *
     * @return список SubTask
     */
    ArrayList<Task> getSubTask();

    /**
     * "Метод удаления всех списков задач"
     */
    void clearAll();

    /**
     * "Метод удаления всего списка Task"
     */
    void clearTask();

    /**
     * "Метод удаления списка EpicTask и SubTask"
     */
    void clearEpic();

    /**
     * "Метод удаления списка SubTask"
     */
    void clearSubTask();

    /**
     * "Метод получения определённого Task по ID"
     *
     * @param id Task id
     * @return объект списка Task
     */
    Task getTaskById(int id);

    /**
     * "Метод получения определённого Epic по ID"
     *
     * @param id Epic id
     * @return объект списка Epic
     */
    Task getEpicById(int id);

    /**
     * "Метод получения определённого SubTask по ID"
     *
     * @param id SubTask id
     * @return объект списка SubTask
     */
    Task getSubTaskById(int id);

    /**
     * "Метод создания Task"
     *
     * @param task объект Task
     */
    void addTask(Task task);

    /**
     * "Метод создания EpicTask"
     *
     * @param epic объект Epic
     */
    void addEpicTask(Epic epic);

    /**
     * "Метод создания SubTask"
     *
     * @param subtask объект SubTask
     */
    void addSubTask(Subtask subtask);

    /**
     * "Метод изменения определенного Task по ID"
     *
     * @param task объект Task
     */
    void changeTask(Task task);

    /**
     * "Метод изменения определенного Epic по ID"
     *
     * @param epic объект Epic
     */
    void changeEpic(Epic epic);

    /**
     * "Метод изменения определенного по ID"
     *
     * @param subtask объект SubTask
     */
    void changeSubTask(Subtask subtask);

    /**
     * "Метод удаления определенного Task по ID"
     *
     * @param id Task id
     */
    void delTask(int id);

    /**
     * "Метод удаления определенного EpicTask по ID"
     *
     * @param id EpicTask id
     */
    void delEpic(int id);

    /**
     * "Метод удаления определенного SubTask по ID"
     *
     * @param id SubTask id
     */
    void delSubTask(int id);


    /**
     * "Метод получения списка всех SubTask определённого Epic"
     *
     * @param id Epic id
     * @return Список SubTask
     */
    ArrayList<Subtask> getSubtaskListEpics(int id);

    /**
     * "Метод определения статуса EpicTask при создании или изменении SubTask"
     *
     * @param epic Epic id
     */
    void updateEpicStatus(Epic epic);

    List<Task> getAllTasks();
}
