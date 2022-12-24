package manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import http.typeAdapters.LocalDateTimeAdapter;
import manager.history.HistoryManager;
import manager.history.InMemoryHistoryManager;

import java.io.File;
import java.time.LocalDateTime;

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


    /**
     * "Метод трекера по умолчанию с записью в файл хранения задач из памяти"
     *
     * @return Задачи
     */
    public static FileBackedTasksManager loadFromFile() {
        FileBackedTasksManager manager = getDefaultFileBackedTaskManager();
        manager.loadFromFile();
        return manager;
    }

    /**
     * "Статический метод создания файла хранения задач"
     *
     * @return
     */
    public static FileBackedTasksManager getDefaultFileBackedTaskManager() {
        return new FileBackedTasksManager(taskStorageFile);
    }

    public static Gson getGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        return gsonBuilder.create();
    }

    public static http.HttpTaskManager getDefaultHttpTaskManager() {
        return new http.HttpTaskManager();
    }

}