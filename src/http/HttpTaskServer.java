package http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import manager.Managers;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_CHARSET;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private final HttpServer server;
    private final HttpTaskManager httpTaskManager;

    private final Gson gson;

    public HttpTaskServer() throws IOException {
        this(Managers.getDefaultHttpTaskManager());
    }

    public HttpTaskServer(HttpTaskManager defaultHttpTaskManager) throws IOException {
        this.httpTaskManager = defaultHttpTaskManager;
        //this.taskManager.loadFromFile();
        gson = Managers.getGson();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", this::handle);
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    private void handle(HttpExchange exchange) {
        try {
            final String path = exchange.getRequestURI().getPath().replaceFirst("/tasks/", "");
            switch (path) {
                case "task":
                    handleTasks(exchange);
                    break;
                case "subtask":
                    handleSubtask(exchange);
                    break;
                case "epic":
                    handleEpic(exchange);
                    break;
                case "history":
                    handleHistory(exchange);
                    break;
                default:
            }
        } catch (IOException exception) {
            System.out.println("Ошибка при обработке запроса");
        } finally {
            exchange.close();
        }
    }

    private void handleTasks(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String query = exchange.getRequestURI().getQuery();
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
        switch (requestMethod) {
            case "GET": {
                if (query != null) {
                    String taskId = query.substring(3);
                    Task task = httpTaskManager.getTaskById(Integer.parseInt(taskId));
                    String response = gson.toJson(task);
                    sendText(exchange, response);
                    return;
                } else {
                    String response = gson.toJson(httpTaskManager.getTask());
                    sendText(exchange, response);
                    return;
                }
            }
            case "POST": {
                try {
                    Task task = gson.fromJson(body, Task.class);
                    httpTaskManager.addTask(task);
                    exchange.sendResponseHeaders(200, 0);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return;
            }
            case "DELETE": {
                if (query != null) {
                    String taskId = query.substring(3);
                    httpTaskManager.delTask(Integer.parseInt(taskId));
                    exchange.sendResponseHeaders(200, 0);
                } else {
                    httpTaskManager.clearTask();
                    exchange.sendResponseHeaders(200, 0);
                }
            }
            default: {
                System.out.println("Ждём GET/POST/DELETE, а получили - " + requestMethod);
                exchange.sendResponseHeaders(405, 0);
            }
        }
    }

    private void handleEpic(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String query = exchange.getRequestURI().getQuery();
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
        switch (requestMethod) {
            case "GET": {
                if (query != null) {
                    String idEpic = query.substring(3);
                    Epic epic = (Epic) httpTaskManager.getEpicById(Integer.parseInt(idEpic));
                    String response = gson.toJson(epic);
                    sendText(exchange, response);
                    return;
                } else {
                    String response = gson.toJson(httpTaskManager.getEpicTask());
                    sendText(exchange, response);
                    return;
                }
            }
            case "POST": {
                try {
                    Epic epic = gson.fromJson(body, Epic.class);
                    httpTaskManager.addEpicTask(epic);
                    exchange.sendResponseHeaders(200, 0);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return;
            }
            case "DELETE": {
                if (query != null) {
                    String epicId = query.substring(3);
                    httpTaskManager.delEpic(Integer.parseInt(epicId));
                    exchange.sendResponseHeaders(200, 0);
                } else {
                    httpTaskManager.clearEpic();
                    exchange.sendResponseHeaders(200, 0);
                }
            }
            default: {
                System.out.println("Ждём GET/POST/DELETE, а получили - " + requestMethod);
                exchange.sendResponseHeaders(405, 0);
            }
        }
    }

    private void handleSubtask(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String query = exchange.getRequestURI().getQuery();
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
        switch (requestMethod) {
            case "GET": {
                if (query != null) {
                    String subtaskId = query.substring(3);
                    Subtask subtask = (Subtask) httpTaskManager.getSubTaskById(Integer.parseInt(subtaskId));
                    String response = gson.toJson(subtask);
                    sendText(exchange, response);
                    return;
                } else {
                    String response = gson.toJson(httpTaskManager.getSubTask());
                    sendText(exchange, response);
                    return;
                }
            }
            case "POST": {
                try {
                    Subtask subtask = gson.fromJson(body, Subtask.class);
                    httpTaskManager.addSubTask(subtask);
                    exchange.sendResponseHeaders(200, 0);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return;
            }
            case "DELETE": {
                if (query != null) {
                    String subtaskId = query.substring(3);
                    httpTaskManager.delSubTask(Integer.parseInt(subtaskId));
                    exchange.sendResponseHeaders(200, 0);
                } else {
                    httpTaskManager.clearSubTask();
                    exchange.sendResponseHeaders(200, 0);
                }
            }
            default: {
                System.out.println("Ждём GET/POST/DELETE, а получили - " + requestMethod);
                exchange.sendResponseHeaders(405, 0);
            }
        }
    }

    private void handleHistory(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        if (requestMethod.equals("GET")) {
            List<Task> history = httpTaskManager.getHistory();
            String response = gson.toJson(history);
            sendText(exchange, response);
        }
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        server.start();
    }

    public void stop() {
        server.stop(0);
        System.out.println("Сервер на порту " + PORT + "был остановлен");
    }


    protected String readText(HttpExchange h) throws IOException {
        return new String(h.getRequestBody().readAllBytes(), UTF_8);
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
    }
}


