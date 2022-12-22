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

import static org.junit.jupiter.api.Assertions.*;

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
    void getTasks() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type taskType = new TypeToken<ArrayList<Task>>() {
        }.getType();
        List<Task> list = gson.fromJson(response.body(), taskType);

        assertNotNull(list, "Задачи не возвращаются");
        assertEquals(1, list.size(), "Не верное количество задач");
    }

    @Test
    void getEpics() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type taskType = new TypeToken<ArrayList<Task>>() {
        }.getType();
        List<Task> list = gson.fromJson(response.body(), taskType);
        assertNotNull(list, "Задачи не возвращаются");
        assertEquals(1, list.size(), "Не верное количество задач");
    }

    @Test
    void getSubTasks() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type taskType = new TypeToken<ArrayList<Task>>() {
        }.getType();
        List<Task> list = gson.fromJson(response.body(), taskType);
        assertNotNull(list, "Задачи не возвращаются");
        assertEquals(1, list.size(), "Не верное количество задач");
    }

    @Test
    void getTaskById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8080/tasks/task?id=1")).
                GET().
                build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type taskType = new TypeToken<Task>() {
        }.getType();
        Task received = gson.fromJson(response.body(), taskType);
        assertNotNull(received, "Задачи не возвращаются");
    }

    @Test
    void getEpicById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8080/tasks/epic?id=2")).
                GET().
                build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type taskType = new TypeToken<Task>() {
        }.getType();
        Task received = gson.fromJson(response.body(), taskType);
        assertNotNull(received, "Задачи не возвращаются");
    }

    @Test
    void getSubtaskById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8080/tasks/subtask?id=3")).
                GET().
                build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        Type taskType = new TypeToken<Task>() {
        }.getType();
        Task received = gson.fromJson(response.body(), taskType);
        assertNotNull(received, "Задачи не возвращаются");
    }

    @Test
    @DisplayName("HttpTaskManagerTest.addTask.Проверка создания задачи")
    void addTask() throws IOException, InterruptedException {
        Task newTask = new Task("NewMyTask 123", "Description", Status.NEW, LocalDateTime.of(2021, 9, 19, 17, 22), 10);
        fileBackedTasksManager.addTask(newTask);
        System.out.println();
        String str = gson.toJson(newTask);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(str))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        client = HttpClient.newHttpClient();
        url = URI.create("http://localhost:8080/tasks/task?id=3");
        request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }


    @Test
    @DisplayName("HttpTaskManagerTest.addSubTask.Проверка создания SubTask")
    void addSubTask() throws IOException, InterruptedException {
        Subtask newSubtask = new Subtask(2, "NewMySubtask 321", "Description", Status.NEW, LocalDateTime.of(2014, 9, 19, 14, 37), 10);
        fileBackedTasksManager.addSubTask(newSubtask);
        String str = gson.toJson(newSubtask);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(str))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        client = HttpClient.newHttpClient();
        url = URI.create("http://localhost:8080/tasks/subtask?id=3");
        request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }


    @Test
    @DisplayName("HttpTaskManagerTest.deleteAllTask.Проверка удаления удаления Task")
    void deleteAllTask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertNotEquals(response.statusCode() == 200, "При успешном запросе возвращается статус код: 200");
    }

    @Test
    @DisplayName("HttpTaskManagerTest.deleteAllEpic.Проверка удаления удаления Epic")
    void deleteAllEpic() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertNotEquals(response.statusCode() == 200, "При успешном запросе возвращается статус код: 200");
    }

    @Test
    @DisplayName("HttpTaskManagerTest.deleteAllSubTask.Проверка удаления удаления SubTask")
    void deleteAllSubTask() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Успешный статус кода: 200");
    }


    @Test
    @DisplayName("HttpTaskManagerTest.SubtaskRemoveById.Проверка удаления Subtask по id")
    void subtaskRemoveById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask?id=3");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET() //
                .header("Content-Type", "application/json")
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Успешный статус кода: 200");
    }

    @Test
    @DisplayName("HttpTaskManagerTest.taskRemoveById.Проверка удаления Task по id")
    void taskRemoveById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task?id=1");
        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .uri(url)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertNotEquals(response.statusCode() == 200, "Успешный статус кода: 200");
    }

    @Test
    @DisplayName("HttpTaskManagerTest.epicRemoveById.Проверка удаления Epic по id")
    void epicRemoveById() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic?id=2");
        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .uri(url)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertNotEquals(response.statusCode() == 200, "Успешный статус кода: 200");
    }

    @Test
    @DisplayName("HttpTaskManagerTest.getHistory.Проверка получения истории")
    void getHistory() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8080/tasks/history")).
                GET().
                build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
    }

}
