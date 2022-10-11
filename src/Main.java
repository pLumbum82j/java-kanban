/**
 * «Трекер задач»
 * @autor Илья Смирнов
 * @version v1.0
 */

import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        System.out.println("Тесты для проверки");

        Task task1 = new Task("MyTask 1-1", "Description", "NEW");
        Task task2 = new Task("MyTask 2-2", "Description", "IN PROGRESS");
        Task task3 = new Task("MyTask 3-3", "Description", "DONE");
        Task task4 = new Task("MyTask 4-4", "Description");
        Epic epic1 = new Epic("MyEpic 1-5", "Description", "NEW");
        Epic epic2 = new Epic("MyEpic 2-6", "Description", "IN PROGRESS");
        Epic epic3 = new Epic("MyEpic 3-7", "Description", "DONE");
        Epic epic4 = new Epic("MyEpic 4-8", "Description");
        Subtask subtask1 = new Subtask(5, "MySubtask 1-9", "Description", "IN PROGRESS");
        Subtask subtask2 = new Subtask(5, "MySubtask 2-10", "Description", "DONE");
        Subtask subtask3 = new Subtask(6, "MySubtask 3-11", "Description", "DONE");
        Subtask subtask4 = new Subtask(6, "MySubtask 4-12", "Description");

        // Пункт 2.4
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addTask(task4);
        taskManager.addEpicTask(epic1);
        taskManager.addEpicTask(epic2);
        taskManager.addEpicTask(epic3);
        taskManager.addEpicTask(epic4);
        taskManager.addSubTask(subtask1);
        taskManager.addSubTask(subtask2);
        taskManager.addSubTask(subtask3);
        taskManager.addSubTask(subtask4);
        System.out.println("Все Task/Epic/Subtask загружены");

        // Пункт 2.1
        //System.out.println("Список Task :" + taskManager.getTask());
        //System.out.println("Список Epic :" + taskManager.getEpicTask());
        //System.out.println("Список Subtask :" + taskManager.getSubTask());

        // Пункт 2.2
        //taskManager.clearAll();
        //taskManager.clearTask();
        //taskManager.clearEpic();
        //taskManager.clearSubTask();

        // Пункт 2.3
        //System.out.println(taskManager.getTaskId(3));
        //System.out.println(taskManager.getEpicId(6));
        //System.out.println(taskManager.getSubTaskId(9));

        // Пункт 2.5
        //taskManager.changeTask(new Task(2, "MyTask 222", "Description", "IN PROGRESS"));
        //System.out.println("Список Task :" + taskManager.getTask());
        //taskManager.changeEpic(new Epic(5,"MyEpic 777", "Description", "IN PROGRESS"));
        //System.out.println("Список Epic :" + taskManager.getEpicTask());
        //taskManager.changeSubTask(new Subtask(5,9,"MySubtask 999", "Description", "DONE"));
        //System.out.println("Список Subtask :" + taskManager.getSubTask());
        //System.out.println("Список Epic :" + taskManager.getEpicTask());


        // Пункт 2.6
        //taskManager.delTask(3);
        //taskManager.delEpic(6);
        //System.out.println("Список Epic :" + taskManager.getEpicTask());
        //taskManager.delSubTask(9);
        // System.out.println("Список Subtask :" + taskManager.getSubTask());

        // Пункт 3.1
        //System.out.println(taskManager.getSubtaskListEpics(6));

    }
}
