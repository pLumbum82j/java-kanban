import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        System.out.println("Тесты для проверки");

        Task task1 = new Task("MyTask 1", "Description", "NEW");
        Task task2 = new Task("MyTask 2", "Description", "IN PROGRESS");
        Task task3 = new Task("MyTask 3", "Description", "DONE");
        Epic epic1 = new Epic("MyEpic 1", "Description", "NEW");
        Epic epic2 = new Epic("MyEpic 2", "Description", "IN PROGRESS");
        Epic epic3 = new Epic("MyEpic 3", "Description", "DONE");
        Epic epic4 = new Epic("MyEpic 4", "Description");
        Subtask subtask1 = new Subtask(5, "MySubtask 1", "Description", "NEW");
        Subtask subtask2 = new Subtask(5, "MySubtask 2", "Description", "IN PROGRESS");
        Subtask subtask3 = new Subtask(6, "MySubtask 3", "Description", "DONE");
        // Пункт 2.4
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addEpicTask(epic1);
        taskManager.addEpicTask(epic2);
        taskManager.addEpicTask(epic3);
        taskManager.addEpicTask(epic4);
        taskManager.addSubTask(subtask1);
        taskManager.addSubTask(subtask2);
        taskManager.addSubTask(subtask3);
        System.out.println("Все Task/Epic/Subtask загружены");

        // Пункт 2.1
        System.out.println("Список Task :" + taskManager.getTask());
        System.out.println("Список Epic :" + taskManager.getEpicTask());
        System.out.println("Список Subtask :" + taskManager.getSubTask());

        // Пункт 2.2
        //taskManager.clearAll();
        //taskManager.clearTask();
        //taskManager.clearEpic(); - нужно уточнить, когда удаляются эпики то удаляется ли сабтаски
        //taskManager.clearSubTask(); - Нужно добавить пересчёт эпиков по статусу

        // Пункт 2.3
        System.out.println(taskManager.getTaskId(3));
        System.out.println(taskManager.getEpicId(6));
        System.out.println(taskManager.getSubTaskId(9));

        // Пункт 2.5 - нужно уточнить, будет ли таска получать ID из метода или юзать генератор
        // - нужно уточнить можно ли удалять таску перед заменой
        taskManager.changeTask(2, new Task("MyTask 333", "Description", "IN PROGRESS"));
        System.out.println("Список Task :" + taskManager.getTask());

        // Пункт 2.6
        //taskManager.delTask(3);
        //taskManager.delEpic(6); <<<<< Доделать, нужен пересчёт updateEpicStatus(epic)
        //taskManager.delSubTask(9); <<<<< Доделать, нужен пересчёт updateEpicStatus(epic)

    }
}
