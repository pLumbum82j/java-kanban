package manager.history;

import task.Task;

import java.util.List;

public interface HistoryManager {
    /**
     * "Спринт 4 - Добавление задачи в историю просмотров"
     * @param task - задача
     */
    void add(Task task);

    /**
     * "Спринт 4 - Получение истории просмотров"
     */
    List<Task> getHistory();
}
