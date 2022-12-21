package tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import http.HttpTaskServer;
import manager.FileBackedTasksManager;
import manager.Managers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import task.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HttpTaskManagerTest {
     HttpTaskServer server;
    FileBackedTasksManager fileBackedTasksManager;
    private final Gson gson = Managers.getGson();

    @BeforeEach
    void createHttpTaskManagerTest() throws IOException {
        server = new HttpTaskServer();
        fileBackedTasksManager = Managers.getDefaultFileBackedTaskManager();
        Task task = new Task("MyTask 1-1", "Description", Status.NEW, LocalDateTime.of(2022, 9, 19, 17, 22), 10);
        Epic epic = new Epic("MyEpic 1-5", "Description", Status.NEW);
        fileBackedTasksManager.addTask(task);
        fileBackedTasksManager.addEpicTask(epic);
        Subtask subtask = new Subtask(2, "MySubtask 1-9", "Description", Status.NEW, LocalDateTime.of(2012, 9, 19, 14, 37), 10);
        fileBackedTasksManager.addSubTask(subtask);

        server.start();
    }

    @AfterEach
    void tearDown() {
        server.stop();
    }

    @Test
    @DisplayName("HttpTaskManagerTest.getTasks.Проверка получения Tasks")
    void getTasks() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(url)
                .GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type taskType = new TypeToken<ArrayList<Task>>() {
        }.getType();
        List<Task> list = gson.fromJson(response.body(), taskType);

        assertNotNull(list, "Задачи не возвращаются");
        assertEquals(1, list.size(), "Не верное количество задач");
    }

}
