package manager.history;

import task.Task;

import java.util.List;

/**
 * "Интерфейс открытых методов для использования любыми History менеджерами"
 */
public interface HistoryManager {
    /**
     * Мотод добавления задачи в историю просмотров"
     *
     * @param task Задача
     */
    void add(Task task);

    /**
     * "Метод возвращения списка истории"
     *
     * @return Список задач
     */
    List<Task> getHistory();

    /**
     * "Удаления задачи из истории просмотра"
     * @param id Задачи
     */
    void remove(int id);

}
