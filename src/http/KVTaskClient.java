package http;

import exceprion.KVTaskClientLoadException;
import exceprion.KVTaskClientPutException;
import exceprion.KVTaskClientRegisterException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * "Класс обменивается информацией между HttpTaskManager и KVServer'ом" (реализация HTTP-клиента)
 */
public class KVTaskClient {
    private final String apiToken;
    private final String url;

    public KVTaskClient() {
        url = "http://localhost:8078/";
        apiToken = register(url);
    }

    /**
     * "Метод получения API-Token'a при регистрации из класса KVServer"
     *
     * @param url ссылка
     * @return apiToken
     */
    public String register(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url + "register"))
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());
            return send.body();
        } catch (Exception e) {
            throw new KVTaskClientRegisterException(e);
        }
    }

    /**
     * "Метод добавления задач на KVServer"
     *
     * @param key  Экземпляр ключа задачи
     * @param json Экземпляр задачи
     */
    public void put(String key, String json) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .uri(URI.create(url + "save/" + key + "?API_TOKEN=" + apiToken))
                    .header("Content-Type", "application/json")
                    .build();
            client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        } catch (IOException | InterruptedException e) {
            throw new KVTaskClientPutException("Не удалось сохранить данные", e);
        }
    }

    /**
     * "Метод считывания состояния загрузки с KVServer'a"
     *
     * @param key Экземпляр ключа
     * @return Экземпляр задачи
     */
    public String load(String key) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url + "load/" + key + "?API_TOKEN=" + apiToken))
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new KVTaskClientLoadException("Во время запроса произошла ошибка", e);
        }
    }
}
