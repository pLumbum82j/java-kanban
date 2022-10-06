import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        System.out.println("Поехали!");
        Task task1 = new Task("MyTask", "Description", "NEW");
        Task task2 = new Task("MyTask 2", "Description", "IN PROGRESS");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
/*
        //Тесты


        Subtask subtask1 = new Subtask();
        Epic epic1 = new Epic();


        taskManager.addTask(subtask1);
        taskManager.addTask(epic1);
*/
    }
}
