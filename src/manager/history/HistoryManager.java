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
     * "Метод получения истории просмотров"
     *
     * @return Список задач
     */
    List<Task> getHistory();
}
