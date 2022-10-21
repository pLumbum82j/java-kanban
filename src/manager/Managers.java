package manager;

import manager.history.HistoryManager;
import manager.history.InMemoryHistoryManager;

/**
 * "Утилитарный класс"
 */
public class Managers {
    /**
     * "Метод трекера по умолчанию"
     *
     * @return Объекты класса InMemoryTaskManager
     */
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    /**
     * "Метод истории просмотров"
     *
     * @return Объекты класса InMemoryHistoryManager
     */
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}