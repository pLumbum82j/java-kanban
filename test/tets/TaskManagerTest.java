package tets;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

abstract class TaskManagerTest<T extends TaskManager> {
    protected T manager;

    protected Task addTask() {
        return new Task(1, "MyTask 1-1", "Description", Status.NEW, LocalDateTime.of(2022, 9, 19, 14, 1), 10);
    }

    protected Epic addEpicTask() {

        return new Epic("MyEpic 1-5", "Description", Status.NEW);
    }

    protected Subtask addSubTask(Epic epic) {
        return new Subtask(5, "MySubtask 1-9", "Description", Status.IN_PROGRESS, LocalDateTime.of(2014, 9, 19, 14, 37), 10);
    }

    @Test
    public void shoulAddTask() {
        Task task = addTask();
        manager.addTask(task);
        List<Task> tasks = manager.getAllTasks();
        assertNotNull(task.getStatus());
        assertEquals(Status.NEW, task.getStatus());
        assertEquals(List.of(task), tasks);
    }
}