package manager;

import manager.history.HistoryManager;
import manager.history.InMemoryHistoryManager;

import java.io.File;

/**
 * "Утилитарный класс"
 */
public class Managers {

    private static File taskStorageFile = new File("tasks.csv");

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

    public static FileBackedTasksManager loadFromFile(){
        FileBackedTasksManager manager = getDefaultFileBackedTaskManager();
        manager.loadFromFile();
        return manager;
    }

    public  static  FileBackedTasksManager getDefaultFileBackedTaskManager(){
        return  new FileBackedTasksManager(taskStorageFile);
    }

}