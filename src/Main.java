/**
 * «Трекер задач»
 * @autor Илья Смирнов
 * @version v2.0
 */

import manager.Managers;
import manager.Status;
import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

public class Main {

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
        Subtask subtask3 = new Subtask(6, "MySubtask 3-11", "Description", Status.DONE);
        Subtask subtask4 = new Subtask(6, "MySubtask 4-12", "Description");

        // Пункт 2.4
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

        // Спринт 4
        manager.getTaskId(222); // проверка на null (несуществующий task)
        manager.getTaskId(1);
        manager.getTaskId(2);
        manager.getTaskId(3);
        manager.getTaskId(4);
        manager.getEpicId(5);
        manager.getEpicId(6);
        manager.getEpicId(7);
        manager.getEpicId(8);
        manager.getSubTaskId(9);
        manager.getSubTaskId(10);
        System.out.println(manager.getHistory());
        System.out.println("Проверка, что история содержит id с 1 по 10");
        System.out.println("--------------");
        manager.getSubTaskId(11);
        manager.getSubTaskId(12);
        System.out.println(manager.getHistory());
        System.out.println("Проверка, что история содержит id с 3 по 12 т.к. вызывали 2-а SubTask'a и положили в конец списка");
        System.out.println("--------------");
        manager.getTaskId(1);
        manager.getTaskId(2);
        manager.getTaskId(3);
        manager.getTaskId(4);
        manager.getEpicId(5);
        System.out.println(manager.getHistory());
        System.out.println("Проверка, что история содержит id с 8 по 12 и с 1 по 5 т.к. вызывали 4-а Task'a и 1-н Epic и положили в конец списка");
        System.out.println("--------------");

        // Спринт 3
        // Пункт 2.1
//        System.out.println("Список Task :" + manager.getTask());
//        System.out.println("Список Epic :" + manager.getEpicTask());
//        System.out.println("Список Subtask :" + manager.getSubTask());

        // Пункт 2.2
//        manager.clearAll();
//        manager.clearTask();
//        manager.clearEpic();
//        manager.clearSubTask();

        // Пункт 2.3
//        System.out.println(manager.getTaskId(3));
//        System.out.println(manager.getEpicId(6));
//        System.out.println(manager.getSubTaskId(9));

        // Пункт 2.5
//        manager.changeTask(new Task(2, "MyTask 222", "Description", Status.IN_PROGRESS));
//        System.out.println("Список Task :" + manager.getTask());
//        manager.changeEpic(new Epic(5,"MyEpic 777", "Description", Status.DONE));
//        System.out.println("Список Epic :" + manager.getEpicTask());
//        manager.changeSubTask(new Subtask(5,9,"MySubtask 999", "Description", Status.DONE));
//       System.out.println("Список Subtask :" + manager.getSubTask());
//        System.out.println("Список Epic :" + manager.getEpicTask());


        // Пункт 2.6
//        manager.delTask(3);
//        manager.delEpic(6);
//        System.out.println("Список Epic :" + manager.getEpicTask());
//        manager.delSubTask(9);
//         System.out.println("Список Subtask :" + manager.getSubTask());

        // Пункт 3.1
//        System.out.println(manager.getSubtaskListEpics(6));

    }
}
