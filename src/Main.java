/**
 * «Трекер задач»
 *
 * @autor Илья Смирнов
 * @version v8.1
 */
import com.google.gson.Gson;
import http.HttpTaskServer;
import http.KVServer;
import manager.Managers;
import task.Status;
import task.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    static Gson gson= Managers.getGson();
    static KVServer kvServerTest;
    static HttpTaskServer httpServerTest;
    public static void main(String[] args) throws IOException {
        kvServerTest = new KVServer();
        kvServerTest.start();
        httpServerTest = new HttpTaskServer();
        httpServerTest.start();

        Task task3 = new Task("MyTask 333", "Description", Status.DONE);
        String str = gson.toJson(task3);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(str))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        client = HttpClient.newHttpClient();
        url = URI.create("http://localhost:8080/tasks/task?id=1");
        request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

         kvServerTest.stop();
        httpServerTest.stop();


    }
}