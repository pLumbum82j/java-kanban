package tets;

import manager.history.HistoryManager;
import manager.history.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Status;
import task.Task;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryHistoryManagerTest {
    HistoryManager manager;
    private int id = 0;

    public int generateId() {
        return ++id;
    }

    protected Task addTask() {
        return new Task("MyTask 123", "Description", Status.NEW, LocalDateTime.now(), 0);
    }

    @BeforeEach
    public void beforeEach() {
        manager = new InMemoryHistoryManager();
    }

    @Test
    public void shouldAddTasksToHistory() {
        Task task1 = addTask();
        int newTaskId1 = generateId();
        task1.setId(newTaskId1);
        Task task2 = addTask();
        int newTaskId2 = generateId();
        task2.setId(newTaskId2);
        Task task3 = addTask();
        int newTaskId3 = generateId();
        task3.setId(newTaskId3);
        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        assertEquals(List.of(task1, task2, task3), manager.getHistory());
    }

    @Test
    public void shouldAddDoubleTasksToHistory() {
        Task task1 = addTask();
        int newTaskId1 = generateId();
        task1.setId(newTaskId1);
        manager.add(task1);
        manager.add(task1);
        assertEquals(List.of(task1), manager.getHistory());
    }

    @Test
    public void shouldRemoveBeginningTask() {
        Task task1 = addTask();
        int newTaskId1 = generateId();
        task1.setId(newTaskId1);
        Task task2 = addTask();
        int newTaskId2 = generateId();
        task2.setId(newTaskId2);
        Task task3 = addTask();
        int newTaskId3 = generateId();
        task3.setId(newTaskId3);
        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        manager.remove(task1.getId());
        assertEquals(List.of(task2, task3), manager.getHistory());
    }

    @Test
    public void shouldRemoveMiddleTask() {
        Task task1 = addTask();
        int newTaskId1 = generateId();
        task1.setId(newTaskId1);
        Task task2 = addTask();
        int newTaskId2 = generateId();
        task2.setId(newTaskId2);
        Task task3 = addTask();
        int newTaskId3 = generateId();
        task3.setId(newTaskId3);
        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        manager.remove(task2.getId());
        assertEquals(List.of(task1, task3), manager.getHistory());
    }

    @Test
    public void shouldRemoveEndingTask() {
        Task task1 = addTask();
        int newTaskId1 = generateId();
        task1.setId(newTaskId1);
        Task task2 = addTask();
        int newTaskId2 = generateId();
        task2.setId(newTaskId2);
        Task task3 = addTask();
        int newTaskId3 = generateId();
        task3.setId(newTaskId3);
        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        manager.remove(task3.getId());
        assertEquals(List.of(task1, task2), manager.getHistory());
    }

    @Test
    public void shouldRemoveOnlyOneTask() {
        Task task = addTask();
        int newTaskId = generateId();
        task.setId(newTaskId);
        manager.add(task);
        manager.remove(task.getId());
        assertEquals(Collections.EMPTY_LIST, manager.getHistory());
    }

    @Test
    public void shouldHistoryIsEmpty() {
        Task task1 = addTask();
        int newTaskId1 = generateId();
        task1.setId(newTaskId1);
        Task task2 = addTask();
        int newTaskId2 = generateId();
        task2.setId(newTaskId2);
        Task task3 = addTask();
        int newTaskId3 = generateId();
        task3.setId(newTaskId3);
        manager.remove(task1.getId());
        manager.remove(task2.getId());
        manager.remove(task3.getId());
        assertEquals(Collections.EMPTY_LIST, manager.getHistory());
    }

    @Test
    public void shouldRemoveTaskWithBadId() {
        Task task = addTask();
        int newTaskId = generateId();
        task.setId(newTaskId);
        manager.add(task);
        manager.remove(0);
        assertEquals(List.of(task), manager.getHistory());
    }

}
