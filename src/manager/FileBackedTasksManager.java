package manager;

import manager.history.HistoryManager;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * "Класс сохранения менеджера задач в файл"
 */
public class FileBackedTasksManager extends InMemoryTaskManager {

    private File file;
    private final String TASK_HEADER = "id,type,name,status,description,epic";

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    /**
     * "Метод восстановления из файла"
     */
    public void loadFromFile() {
        boolean tasksRead = false;
        int generatorId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {
                String input = reader.readLine();

                if (input.isEmpty()) {
                    tasksRead = true;
                    continue;
                }
                if (input.equals(TASK_HEADER)) {
                    continue;
                }
                if (tasksRead) {
                    historyFromString(input);
                    break;
                }
                generatorId = fillMaps(generatorId, input);

            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при загрузке из файла");
        }
        this.generatorId = generatorId + 1;

        for (Subtask subtask : subtasks.values()) {
            addSubtaskInEpicWithoutID(subtask);
        }
    }

    /**
     * !!
     * @param generatorID
     * @param input
     * @return
     */
    private int fillMaps(int generatorID, String input) {
        String[] splitLine = input.split(",");

        switch (splitLine[1]) {
            case "TASK":
                Task task = Task.fromString(input);
                tasks.put(task.getId(), task);
                if (generatorID < task.getId())
                    generatorID = task.getId();
                break;
            case "EPIC":
                Epic epic = Epic.fromString(input);
                epics.put(epic.getId(), epic);
                if (generatorID < epic.getId())
                    generatorID = epic.getId();
                break;
            case "SUBTASK":
                Subtask subTask = Subtask.fromString(input);
                subtasks.put(subTask.getId(), subTask);
                if (generatorID < subTask.getId())
                    generatorID = subTask.getId();
                break;
        }
        return generatorID;
    }

    /**
     * "Метод сохранения задач"
     */
    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(TASK_HEADER);
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
     * "Метод преобразования истории задач в строку"
     * @param manager Список задач
     * @return Список ID задач через запятую
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


    /**
     * !!! Сравнение
     * @param manager
     */
    public void compareLoadAndSaveManagers(FileBackedTasksManager manager){
        if(!tasks.equals(manager.tasks)||!epics.equals(manager.epics)||!subtasks.equals(manager.subtasks)
                ||!defaultHistory.getHistory().equals(manager.defaultHistory.getHistory())){
            System.out.println("false");
        }
    }

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

    @Override
    public void clearAll() {
        super.clearAll();
        save();
    }

    @Override
    public void clearTask() {
        super.clearTask();
        save();
    }

    @Override
    public void clearEpic() {
        super.clearEpic();
        save();
    }

    @Override
    public void clearSubTask() {
        super.clearSubTask();
        save();
    }

    @Override
    public void delTask(int id) {
        super.delTask(id);
        save();
    }

    @Override
    public void delEpic(int id) {
        super.delEpic(id);
        save();
    }

    @Override
    public void delSubTask(int id) {
        super.delSubTask(id);
        save();
    }

    @Override
    public Task getTaskById(int id) {
        save();
        return super.getTaskById(id);
    }

    @Override
    public Task getEpicById(int id) {
        save();
        return super.getEpicById(id);
    }

    @Override
    public Task getSubTaskById(int id) {
        save();
        return super.getSubTaskById(id);
    }

    @Override
    public void changeTask(Task task) {
        super.changeTask(task);
        save();
    }

    @Override
    public void changeEpic(Epic epic) {
        super.changeEpic(epic);
        save();
    }

    @Override
    public void changeSubTask(Subtask subtask) {
        super.changeSubTask(subtask);
        save();
    }

    public static void main(String[] args) {
        FileBackedTasksManager manager = Managers.loadFromFile();
        FileBackedTasksManager saveManager = Managers.getDefaultFileBackedTaskManager();
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


        saveManager.addTask(task1);
        saveManager.addTask(task2);
        saveManager.addTask(task3);
        saveManager.addTask(task4);
        saveManager.addEpicTask(epic1);
        saveManager.addEpicTask(epic2);
        saveManager.addEpicTask(epic3);
        saveManager.addEpicTask(epic4);
        saveManager.addSubTask(subtask1);
        saveManager.addSubTask(subtask2);
        saveManager.addSubTask(subtask3);
        saveManager.addSubTask(subtask4);
        System.out.println("Все Task/Epic/Subtask загружены");

        saveManager.getTaskById(1);
        saveManager.getTaskById(2);
        saveManager.getTaskById(3);
        System.out.println(saveManager.getHistory());
        manager.compareLoadAndSaveManagers(saveManager);
    }
}
