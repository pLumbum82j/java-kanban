import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        System.out.println("Поехали!");

        System.out.println("Создаём task");
        Task task1 = new Task("MyTask", "Description", "NEW");
        Task task2 = new Task("MyTask 2", "Description", "IN PROGRESS");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        System.out.println(task1.getId());
        System.out.println(task2.getId());

        Epic epic1 = new Epic("MyEpic 1", "Description", "NEW");
        taskManager.addEpicTask(epic1);
        System.out.println("Эпик #" + epic1.getId() + " Имеет статус " + epic1.getStatus());

        System.out.println("Добавляем subtask в epic");
        Subtask subtask1 = new Subtask(3, "MySubtask 1", "Description", "NEW");
        Subtask subtask2 = new Subtask(22, "MySubtask 2", "Description", "IN PROGRESS");
        Subtask subtask3 = new Subtask(3, "MySubtask 3", "Description", "DONE");
        taskManager.addSubTask(subtask1);
        taskManager.addSubTask(subtask2);
        taskManager.addSubTask(subtask3);
        System.out.println(subtask1.getEpicId() + " - " + subtask1.getId());
        System.out.println(subtask2.getEpicId() + " - " + subtask2.getId());
        System.out.println(subtask3.getEpicId() + " - " + subtask3.getId());

        System.out.println("Список Task :" + taskManager.getTask());
        System.out.println("Список Epic :" + taskManager.getEpicTask());
        System.out.println("Список Subtask :" + taskManager.getSubTask());

        Task task3 = new Task("MyTask 3", "Description", "IN PROGRESS");
        taskManager.addTask(task3);
        System.out.println(task3.getId());
        taskManager.delTask(2);
        System.out.println("Список Task :" + taskManager.getTask());
    }
}
