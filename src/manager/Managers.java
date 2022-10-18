package manager;

import manager.history.HistoryManager;
import manager.history.InMemoryHistoryManager;

/**
 * "Спринт 4 - Утилитарный класс"
 */
public class Managers {
    /**
     * "Спринт 4 - Метод трекера по умолчанию"
     */
    public static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }
    /**
     * "Спринт 4 - Метод истории просмотров"
     */
    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}