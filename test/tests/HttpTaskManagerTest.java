package tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import http.HttpTaskServer;
import http.KVServer;
import manager.Managers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskManagerTest {
    HttpTaskServer httpServer;
    KVServer kvServer;
    private final Gson gson = Managers.getGson();

    @BeforeEach
    void createHttpTaskManagerTest() throws IOException, InterruptedException {
        kvServer = new KVServer();
        kvServer.start();
        httpServer = new HttpTaskServer();
        httpServer.start();

        Task newTask = new Task("NewMyTask 123", "Description", Status.NEW,
                LocalDateTime.of(2021, 9, 19, 17, 22), 10);
        Epic newEpic = new Epic("NewMyEpic 321", "Description", Status.NEW);
        Subtask newSub = new Subtask(2, "NewMySub 231", "Description", Status.NEW,
                LocalDateTime.of(2023, 9, 19, 17, 22), 12);

        String str = gson.toJson(newTask);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(str))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String>  responseTask = client.send(request, HttpResponse.BodyHandlers.ofString());

        str = gson.toJson(newEpic);
        client = HttpClient.newHttpClient();
        url = URI.create("http://localhost:8080/tasks/epic");
        request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(str))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String>  responseEpic = client.send(request, HttpResponse.BodyHandlers.ofString());

        str = gson.toJson(newSub);
        client = HttpClient.newHttpClient();
        url = URI.create("http://localhost:8080/tasks/subtask");
        request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(str))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String>  responseSub = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, responseTask.statusCode(), "При успешном запросе возвращается статус код: 200");
        assertEquals(200, responseEpic.statusCode(), "При успешном запросе возвращается статус код: 200");
        assertEquals(200, responseSub.statusCode(), "При успешном запросе возвращается статус код: 200");


    }

    @AfterEach
    void tearDown() {
        httpServer.stop();
        kvServer.stop();
    }

    @Test
    void shouldGetTasks() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type taskType = new TypeToken<ArrayList<Task>>() {
        }.getType();
        List<Task> list = gson.fromJson(response.body(), taskType);

        assertEquals(200, response.statusCode(), "При успешном запросе возвращается статус код: 200");
        assertNotNull(list, "Задачи не возвращаются");
        assertEquals(1, list.size(), "Не верное количество задач");
    }

    @Test
    void shouldGetEpics() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type taskType = new TypeToken<ArrayList<Epic>>() {
        }.getType();
        List<Epic> list = gson.fromJson(response.body(), taskType);

        assertEquals(200, response.statusCode(), "При успешном запросе возвращается статус код: 200");
        assertNotNull(list, "Задачи не возвращаются");
        assertEquals(1, list.size(), "Не верное количество задач");
    }

    @Test
    void shouldGetSubTasks() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type taskType = new TypeToken<ArrayList<Subtask>>() {
        }.getType();
        List<Subtask> list = gson.fromJson(response.body(), taskType);

        assertEquals(200, response.statusCode(), "При успешном запросе возвращается статус код: 200");
        assertNotNull(list, "Задачи не возвращаются");
        assertEquals(1, list.size(), "Не верное количество задач");
    }

    @Test
    void shouldGetTaskById() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8080/tasks/task?id=1")).
                GET().
                build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type taskType = new TypeToken<Task>() {
        }.getType();
        Task received = gson.fromJson(response.body(), taskType);

        assertEquals(200, response.statusCode(), "При успешном запросе возвращается статус код: 200");
        assertNotNull(received, "Задачи не возвращаются");
    }

    @Test
    void shouldGetEpicById() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8080/tasks/epic?id=2")).
                GET().
                build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type taskType = new TypeToken<Task>() {
        }.getType();
        Task received = gson.fromJson(response.body(), taskType);

        assertEquals(200, response.statusCode(), "При успешном запросе возвращается статус код: 200");
        assertNotNull(received, "Задачи не возвращаются");
    }

    @Test
    void shouldGetSubtaskById() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create("http://localhost:8080/tasks/subtask?id=3")).
                GET().
                build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type taskType = new TypeToken<Task>() {
        }.getType();
        Task received = gson.fromJson(response.body(), taskType);

        assertEquals(200, response.statusCode(), "При успешном запросе возвращается статус код: 200");
        assertNotNull(received, "Задачи не возвращаются");
    }

    @Test
    void shouldAddTask() throws IOException, InterruptedException {
        Task newTask = new Task("NewMyTask 333", "Description", Status.NEW,
                LocalDateTime.of(2025, 9, 19, 17, 22), 10);

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
        url = URI.create("http://localhost:8080/tasks/task?id=4");
        request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "При успешном запросе возвращается статус код: 200");
    }


    @Test
    void shouldAddSubTask() throws IOException, InterruptedException {
        Subtask newSubtask = new Subtask(2, "NewMySubtask 444", "Description", Status.NEW,
                LocalDateTime.of(2014, 9, 19, 14, 37), 10);

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
        url = URI.create("http://localhost:8080/tasks/subtask?id=4");
        request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "При успешном запросе возвращается статус код: 200");
    }


    @Test
    void shouldDeleteAllTasks() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "При успешном запросе возвращается статус код: 200");
    }

    @Test
    void shouldDeleteAllEpic() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "При успешном запросе возвращается статус код: 200");
    }

    @Test
    void shouldDeleteAllSubTask() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "При успешном запросе возвращается статус код: 200");
    }

    @Test
    void shouldTaskRemoveById() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task?id=1");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String>  response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "При успешном запросе возвращается статус код: 200");
    }

    @Test
    void shouldEpicRemoveById() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/epic?id=2");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String>  response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "При успешном запросе возвращается статус код: 200");
    }

    @Test
    void shouldSubtaskRemoveById() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/subtask?id=3");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "При успешном запросе возвращается статус код: 200");
    }


    @Test
    void shouldGetHistory() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/history"))
                .GET()
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "При успешном запросе возвращается статус код: 200");
    }

}
