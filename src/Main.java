/**
 * «Трекер задач»
 *
 * @autor Илья Смирнов
 * @version v5.4
 */

import manager.Managers;
import task.Status;
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


        manager.getEpicById(5);
        manager.getEpicById(6);
        manager.getEpicById(7);
        manager.getEpicById(8);
        manager.getSubTaskById(9);
        manager.getSubTaskById(10);
        manager.getSubTaskById(11);
        manager.getSubTaskById(12);
        System.out.println(manager.getHistory());
        System.out.println("Проверка, что история содержит id с 5 по 12");

        manager.getSubTaskById(11);
        manager.getSubTaskById(9);
        manager.getSubTaskById(10);
        manager.getEpicById(5);
        manager.getEpicById(7);
        System.out.println(manager.getHistory());
        System.out.println("Проверка, что история изменяется и новый вызванный Task падает в конец списка");
        System.out.println("--------------");
//
//        manager.getTaskById(1);
//        manager.getSubTaskById(11);
//        manager.getEpicById(5);
//        manager.getSubTaskById(9);
//        manager.getSubTaskById(10);
//        System.out.println(manager.getHistory());
//        System.out.println("--------------");
//        manager.delTask(1);
//        System.out.println(manager.getHistory());
//        System.out.println("После удаления Task #1 - он пропал из списка истории");
//        System.out.println();
//        manager.delSubTask(11);
//        System.out.println(manager.getHistory());
//        System.out.println("После удаления SubTask #11 - он пропал из списка истории");
//        System.out.println();
//        manager.getSubTaskById(11);
//        manager.delEpic(5);
//        System.out.println(manager.getHistory());
//        System.out.println("После удаления Epic #5 - он пропал из списка истории, а так же Subtask 9,10,11");
//
//
//        manager.getTaskById(2);
//        manager.getTaskById(3);
//        manager.getTaskById(4);
//        System.out.println(manager.getHistory());
//        System.out.println("--------------");
//        manager.clearTask();
//        System.out.println(manager.getHistory());
//        System.out.println("После отчистки списка - удалились все Task из истории");
//
//        manager.getSubTaskById(9);
//        manager.getSubTaskById(10);
//        manager.getSubTaskById(11);
//        manager.getSubTaskById(12);
//        System.out.println(manager.getHistory());
//        System.out.println("--------------");
//        manager.clearSubTask();
//        System.out.println(manager.getHistory());
//        System.out.println("После отчистки списка - удалились все SubTask из истории");
//
//        manager.getEpicById(5);
//        manager.getEpicById(6);
//        manager.getEpicById(7);
//        manager.getEpicById(8);
//        manager.getSubTaskById(9);
//        manager.getSubTaskById(10);
//        manager.getSubTaskById(11);
//        manager.getSubTaskById(12);
//        System.out.println(manager.getHistory());
//        System.out.println("--------------");
//        manager.clearEpic();
//        System.out.println(manager.getHistory());
//        System.out.println("После отчистки списка - удалились все Epic и SubTask из истории");
//
//        manager.getTaskById(1);
//        manager.getTaskById(2);
//        manager.getTaskById(3);
//        manager.getTaskById(4);
//        manager.getEpicById(5);
//        manager.getEpicById(6);
//        manager.getEpicById(7);
//        manager.getEpicById(8);
//        manager.getSubTaskById(9);
//        manager.getSubTaskById(10);
//        manager.getSubTaskById(11);
//        manager.getSubTaskById(12);
//        System.out.println(manager.getHistory());
//        System.out.println("--------------");
//        manager.clearAll();
//        System.out.println(manager.getHistory());
//        System.out.println("Удалили все виды задач из списка, следовательно удалили всю историю");
    }
}
