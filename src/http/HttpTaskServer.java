package http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import manager.FileBackedTasksManager;
import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_CHARSET;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private HttpServer server;
    private TaskManager taskManager;
    private FileBackedTasksManager fileBackedTasksManager;
    private Gson gson;

    public HttpTaskServer() throws IOException {
        this(Managers.getDefaultFileBackedTaskManager());
    }

    public HttpTaskServer(FileBackedTasksManager fileBackedTasksManager) throws IOException {
        //Path file = Path.of("test.csv");
        this.fileBackedTasksManager = Managers.getDefaultFileBackedTaskManager();
        //this.fileBackedTasksManager.loadFromFile();
        gson = Managers.getGson();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", this::handle);
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

//    public static void main(String[] args) throws IOException {
//        HttpTaskServer httpTaskServer = new HttpTaskServer();
//        httpTaskServer.start();
//        httpTaskServer.stop();
//    }

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
                    Task task = taskManager.getTaskById(Integer.parseInt(taskId));
                    String response = gson.toJson(task);
                    sendText(exchange, response);
                    return;
                } else {
                    String response = gson.toJson(taskManager.getTask());
                    sendText(exchange, response);
                    return;
                }
            }
            case "POST": {
                try {
                    Task task = gson.fromJson(body, Task.class);
                    taskManager.addTask(task);
                    exchange.sendResponseHeaders(200, 0);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return;
            }
            case "DELETE": {
                if (query != null) {
                    String taskId = query.substring(3);
                    taskManager.delTask(Integer.parseInt(taskId));
                    exchange.sendResponseHeaders(200, 0);
                } else {
                    taskManager.clearTask();
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
                    Epic epic = (Epic) taskManager.getEpicById(Integer.parseInt(idEpic));
                    String response = gson.toJson(epic);
                    sendText(exchange, response);
                    return;
                } else {
                    String response = gson.toJson(taskManager.getEpicTask());
                    sendText(exchange, response);
                    return;
                }
            }
            case "POST": {
                try {
                    Epic epic = gson.fromJson(body, Epic.class);
                    taskManager.addEpicTask(epic);
                    exchange.sendResponseHeaders(200, 0);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return;
            }
            case "DELETE": {
                if (query != null) {
                    String epicId = query.substring(3);
                    taskManager.delEpic(Integer.parseInt(epicId));
                    exchange.sendResponseHeaders(200, 0);
                } else {
                    taskManager.clearEpic();
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
                    Subtask subtask = (Subtask) taskManager.getSubTaskById(Integer.parseInt(subtaskId));
                    String response = gson.toJson(subtask);
                    sendText(exchange, response);
                    return;
                } else {
                    String response = gson.toJson(taskManager.getSubTask());
                    sendText(exchange, response);
                    return;
                }
            }
            case "POST": {
                try {
                    Subtask subtask = gson.fromJson(body, Subtask.class);
                    taskManager.addSubTask(subtask);
                    exchange.sendResponseHeaders(200, 0);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return;
            }
            case "DELETE": {
                if (query != null) {
                    String subtaskId = query.substring(3);
                    taskManager.delSubTask(Integer.parseInt(subtaskId));
                    exchange.sendResponseHeaders(200, 0);
                } else {
                    taskManager.clearSubTask();
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
            List<Task> history = taskManager.getHistory();
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
        System.out.println("Остановили сервер на порту " + PORT);
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

