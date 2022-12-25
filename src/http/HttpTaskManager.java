package http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import manager.FileBackedTasksManager;
import manager.Managers;
import manager.history.InMemoryHistoryManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.lang.reflect.Type;
import java.util.List;

/**
 * "Класс реализующий основные методы трекера (менеджера) задач"
 */
public class HttpTaskManager extends FileBackedTasksManager {
    private final KVTaskClient client;
    private final Gson gson;
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

    public HttpTaskManager() {
        super(null);
        this.gson = Managers.getGson();
        this.client = new KVTaskClient();
    }

    /**
     * "Переопределенный метод считывания информации с KVServer'a через KVClient"
     */
    @Override
    public void loadFromFile() {
        Type tasksType = new TypeToken<List<Task>>() {
        }.getType();
        List<Task> tasks = gson.fromJson(client.load("tasks"), tasksType);
        if (tasks != null) {
            tasks.forEach(task -> {
                int id = task.getId();
                this.tasks.put(id, task);
                this.prioritizedTasks.add(task);
                if (id > generatorId) {
                    generatorId = id;
                }
            });
        }

        Type subtasksType = new TypeToken<List<Subtask>>() {
        }.getType();
        List<Subtask> subtasks = gson.fromJson(client.load("subtasks"), subtasksType);
        if (subtasks != null) {
            subtasks.forEach(subtask -> {
                int id = subtask.getId();
                this.subtasks.put(id, subtask);
                this.prioritizedTasks.add(subtask);
                if (id > generatorId) {
                    generatorId = id;
                }
            });
        }

        Type epicsType = new TypeToken<List<Epic>>() {
        }.getType();
        List<Epic> epics = gson.fromJson(client.load("epics"), epicsType);
        if (epics != null) {
            epics.forEach(epic -> {
                int id = epic.getId();
                this.epics.put(id, epic);
                this.prioritizedTasks.add(epic);
                if (id > generatorId) {
                    generatorId = id;
                }
            });
        }

        Type historyType = new TypeToken<List<Task>>() {
        }.getType();
        List<Task> history = gson.fromJson(client.load("history"), historyType);

        if (history != null) {
            for (Task task : history) {
                inMemoryHistoryManager.add(this.findTask(task.getId()));
            }
        }
    }

    /**
     * "Переопределенный метод сохранения задач в объект KVClient"
     */
    @Override
    public void save() {
        String jsonTasks = gson.toJson(getTask());
        client.put("tasks", jsonTasks);
        String jsonEpics = gson.toJson(getEpicTask());
        client.put("epics", jsonEpics);
        String jsonSubTask = gson.toJson(getSubTask());
        client.put("subtasks", jsonSubTask);
        String jsonHistory = gson.toJson(getHistory());
        client.put("history", jsonHistory);
    }

    protected Task findTask(Integer id) {
        if (tasks.get(id) != null) {
            return tasks.get(id);
        }
        if (epics.get(id) != null) {
            return epics.get(id);
        }
        return subtasks.get(id);

    }
}
