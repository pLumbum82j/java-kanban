package manager;

import manager.history.HistoryManager;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    /**
     * Восстанавливаем FileBackedTasksManager из файла
     *
     * @param file
     * @return
     */
    //public static FileBackedTasksManager loadFromFile(File file) {
    public static void loadFromFile() {
        // Создать через конструктор FileBackedTasksManager
        //FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);

        String allinfo = null;
        String currentString = null;
        int generatorid = 0;
        // Обернём всё в try, пытаясь поймать его IOException
        // Прочитать из файла содержимое  (подсказка из ТЗ)
        try {
            currentString = Files.readString(Path.of("tasks.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(currentString))) {


            while ((currentString = reader.readLine()) != null) {
                allinfo += currentString;
            }
            // Порубить файл по строкам
            String[] buffer = allinfo.split("\n");
            // в цикле проходить все строки (for i=0, i< строк которые мы получили)
            // Десериализуем таск из строки (каждый раз проходим строку через СSVTaskConverter). Например получили Task task = ...
            // if (task.id > genetatorid) , то generatorid = task.id (потом обратить внимание на генерацию ID у новых тасков)
            // Если мы наткнулись на пустую строку , то это история - тогда парсим её (потом делаем break из  цикла парса истории)
            // Добавить task в  какую-то соответсвующую мапу (switc по типу)


            // Привязать сабтаски и эпики
            // Проходимся по сабтаскам и связываем сабтаски и эпики

//        String[] history = buffer[buffer.length - 1].split(",");
//        // Дообработать историю
//        for (int j = 0, j < history.length; j++){
//    int currentId = Integer.parseInt(history[0]);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Пройтись список айдишников из десириализованной истории и подабовлять в историю с помощью
        //уже существуюшего метода historyManager.add(..)

        //Не забываем привязать новый generatorid - ?????????

    }

    /**
     * !! Будет производиться сохранение тасков
     */
    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("id,type,name,status,description,epic");
            writer.newLine();
            for (Task task : tasks.values()) {
                writer.write(task.toString());
            }
            for (Epic epic : epics.values()) {
                writer.write(epic.toString());
            }
            for (Subtask subTask : subtasks.values()) {
                writer.write(subTask.toString());
            }
            writer.newLine();
            writer.write(historyToString(defaultHistory));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ManagerSaveException("Ошибка при создании файла");
        }
    }

    /**
     * @param value
     * @return
     */
    public static List<Integer> historyFromString(String value) {
        List<Integer> valueHistoryFromString = new ArrayList<>();
        String[] splitString = value.split(",");
        for (int i = 0; i < splitString.length; i++) {
            valueHistoryFromString.add(Integer.valueOf(splitString[i]));
        }
        return valueHistoryFromString;
    }

    /**
     * @param manager
     * @return
     */
    private static String historyToString(HistoryManager manager) {
        String valueHistoryToString = "";
        for (int i = 0; i < manager.getHistory().size(); i++) {
            valueHistoryToString += manager.getHistory().get(i).getId() + " ";
        }
        // Не хотел через if, хотел показать, что умею и знаю методы trim и replace
        valueHistoryToString = valueHistoryToString.trim();
        valueHistoryToString = valueHistoryToString.replace(' ', ',');
        return valueHistoryToString;
    }


    // методы получения тасков, добавления, обновления, удаления по всем им овверрайдить save
    @Override
    public void addSubTask(Subtask subtask) {
        super.addSubTask(subtask);
        save();
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpicTask(Epic epic) {
        super.addEpicTask(epic);
        save();
    }

    @Override
    public List<Task> getHistory() {
        save();
        return super.getHistory();
    }

    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        System.out.println("Тесты для проверки");

        Task task1 = new Task("MyTask 1-1", "Description", Status.NEW);
        Task task2 = new Task("MyTask 2-2", "Description", Status.IN_PROGRESS);
        Task task3 = new Task("MyTask 3-3", "Description", Status.DONE);
        Task task4 = new Task("MyTask 4-4", "Description");
        Epic epic1 = new Epic("MyEpic 1-5", "Description", Status.NEW);
        Epic epic2 = new Epic("MyEpic 2-6", "Description", Status.IN_PROGRESS);
        Epic epic3 = new Epic("MyEpic 3-7", "Description", Status.DONE);
        Epic epic4 = new Epic("MyEpic 4-8", "Description");
        Subtask subtask1 = new Subtask(5, "MySubtask 1-9", "Description", Status.IN_PROGRESS);
        Subtask subtask2 = new Subtask(5, "MySubtask 2-10", "Description", Status.DONE);
        Subtask subtask3 = new Subtask(5, "MySubtask 3-11", "Description", Status.DONE);
        Subtask subtask4 = new Subtask(6, "MySubtask 4-12", "Description");


        manager.addTask(task1);
        manager.addTask(task2);
        manager.addTask(task3);
        manager.addTask(task4);
        manager.addEpicTask(epic1);
        manager.addEpicTask(epic2);
        manager.addEpicTask(epic3);
        manager.addEpicTask(epic4);
        manager.addSubTask(subtask1);
        manager.addSubTask(subtask2);
        manager.addSubTask(subtask3);
        manager.addSubTask(subtask4);
        System.out.println("Все Task/Epic/Subtask загружены");

        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getTaskById(3);
        System.out.println(manager.getHistory());
        loadFromFile();
    }
}
