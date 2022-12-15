package tests;

import manager.TaskManager;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    protected T manager;

    protected Task addTask() {
        return new Task(1, "MyTask 1-1", "Description", Status.NEW, LocalDateTime.of(2022, 9, 19, 14, 1), 10);
    }

    protected Epic addEpicTask() {
        return new Epic("MyEpic 1-5", "Description", Status.NEW);
    }

    protected Subtask addSubTask(Epic epic) {
        return new Subtask(1, "MySubtask 1-9", "Description", Status.NEW, LocalDateTime.of(2012, 9, 19, 14, 37), 10);
    }

    @Test
    public void shouldAddTask() {
        Task task = addTask();

        manager.addTask(task);
        List<Task> tasks = manager.getTask();

        assertNotNull(task.getStatus());
        assertEquals(Status.NEW, task.getStatus());
        assertEquals(List.of(task), tasks);
    }

    @Test
    public void shouldAddEpicTask() {
        Epic epic = addEpicTask();

        manager.addEpicTask(epic);
        List<Task> epics = manager.getEpicTask();

        assertNotNull(epic.getStatus());
        assertEquals(Status.NEW, epic.getStatus());
        assertEquals(Collections.EMPTY_LIST, epic.getSubtaskListId());
        assertEquals(List.of(epic), epics);
    }

    @Test
    public void shouldAddSubTask() {
        Epic epic = addEpicTask();
        Subtask subtask = addSubTask(epic);

        manager.addEpicTask(epic);
        manager.addSubTask(subtask);
        List<Task> subtasks = manager.getSubTask();

        assertNotNull(subtask.getStatus());
        assertEquals(epic.getId(), subtask.getEpicId());
        assertEquals(Status.NEW, subtask.getStatus());
        assertEquals(List.of(subtask), subtasks);
        assertEquals(List.of(subtask.getId()), epic.getSubtaskListId());
    }

    @Test
    public void shouldStatusEpicAllNew() {
        Epic epic = addEpicTask();
        Subtask subtask = addSubTask(epic);
        Subtask subtask2 = new Subtask(1, "MySubtask 123", "Description", Status.NEW, LocalDateTime.of(2012, 9, 19, 17, 37), 10);

        manager.addEpicTask(epic);
        manager.addSubTask(subtask);
        manager.addSubTask(subtask2);

        assertNotNull(subtask.getStatus());
        assertNotNull(subtask2.getStatus());
        assertEquals(epic.getId(), subtask.getEpicId());
        assertEquals(epic.getId(), subtask2.getEpicId());
        assertEquals(Status.NEW, subtask.getStatus());
        assertEquals(Status.NEW, subtask2.getStatus());
        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    public void shouldStatusEpicNewAndDone() {
        Epic epic = addEpicTask();
        Subtask subtask = addSubTask(epic);
        Subtask subtask2 = new Subtask(1, "MySubtask 123", "Description", Status.DONE, LocalDateTime.of(2012, 9, 19, 17, 37), 10);

        manager.addEpicTask(epic);
        manager.addSubTask(subtask);
        manager.addSubTask(subtask2);

        assertNotNull(subtask.getStatus());
        assertNotNull(subtask2.getStatus());
        assertEquals(epic.getId(), subtask.getEpicId());
        assertEquals(epic.getId(), subtask2.getEpicId());
        assertEquals(Status.NEW, subtask.getStatus());
        assertEquals(Status.DONE, subtask2.getStatus());
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void shouldStatusEpicAllDone() {
        Epic epic = addEpicTask();
        Subtask subtask = new Subtask(1, "MySubtask 123", "Description", Status.DONE, LocalDateTime.of(2012, 9, 19, 10, 37), 10);
        Subtask subtask2 = new Subtask(1, "MySubtask 321", "Description", Status.DONE, LocalDateTime.of(2012, 9, 19, 17, 37), 10);

        manager.addEpicTask(epic);
        manager.addSubTask(subtask);
        manager.addSubTask(subtask2);

        assertNotNull(subtask.getStatus());
        assertNotNull(subtask2.getStatus());
        assertEquals(epic.getId(), subtask.getEpicId());
        assertEquals(epic.getId(), subtask2.getEpicId());
        assertEquals(Status.DONE, subtask.getStatus());
        assertEquals(Status.DONE, subtask2.getStatus());
        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    public void shouldStatusEpicAllInProgress() {
        Epic epic = addEpicTask();
        Subtask subtask = new Subtask(1, "MySubtask 123", "Description", Status.IN_PROGRESS, LocalDateTime.of(2012, 9, 19, 10, 37), 10);
        Subtask subtask2 = new Subtask(1, "MySubtask 321", "Description", Status.IN_PROGRESS, LocalDateTime.of(2012, 9, 19, 17, 37), 10);

        manager.addEpicTask(epic);
        manager.addSubTask(subtask);
        manager.addSubTask(subtask2);

        assertNotNull(subtask.getStatus());
        assertNotNull(subtask2.getStatus());
        assertEquals(epic.getId(), subtask.getEpicId());
        assertEquals(epic.getId(), subtask2.getEpicId());
        assertEquals(Status.IN_PROGRESS, subtask.getStatus());
        assertEquals(Status.IN_PROGRESS, subtask2.getStatus());
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    void shouldReturnNullWhenAddTaskNull() {
        Task task = manager.addTask(null);

        assertNull(task);
    }

    @Test
    void shouldReturnNullWhenAddEpicNull() {
        Epic epic = manager.addEpicTask(null);

        assertNull(epic);
    }

    @Test
    void shouldReturnNullWhenAddSubtaskNull() {
        Subtask subtask = manager.addSubTask(null);

        assertNull(subtask);
    }

    @Test
    public void shouldUpdateTaskStatusToNew() {
        Task task = addTask();

        manager.addTask(task);
        task.setStatus(Status.NEW);
        manager.updateTaskStatus(task);

        assertEquals(Status.NEW, manager.getTaskById(task.getId()).getStatus());
    }

    @Test
    public void shouldUpdateTaskStatusToInProgress() {
        Task task = addTask();

        manager.addTask(task);
        task.setStatus(Status.IN_PROGRESS);
        manager.updateTaskStatus(task);

        assertEquals(Status.IN_PROGRESS, manager.getTaskById(task.getId()).getStatus());
    }

    @Test
    public void shouldUpdateTaskStatusToDone() {
        Task task = addTask();

        manager.addTask(task);
        task.setStatus(Status.DONE);
        manager.updateTaskStatus(task);

        assertEquals(Status.DONE, manager.getTaskById(task.getId()).getStatus());
    }

    @Test
    public void shouldUpdateEpicStatusToNew() {
        Epic epic = addEpicTask();

        manager.addEpicTask(epic);
        epic.setStatus(Status.NEW);

        assertEquals(Status.NEW, manager.getEpicById(epic.getId()).getStatus());
    }

    @Test
    public void shouldUpdateEpicStatusToInProgress() {
        Epic epic = addEpicTask();

        manager.addEpicTask(epic);
        epic.setStatus(Status.IN_PROGRESS);

        assertEquals(Status.IN_PROGRESS, manager.getEpicById(epic.getId()).getStatus());
    }

    @Test
    public void shouldUpdateEpicStatusToDone() {
        Epic epic = addEpicTask();

        manager.addEpicTask(epic);
        epic.setStatus(Status.DONE);

        assertEquals(Status.DONE, manager.getEpicById(epic.getId()).getStatus());
    }

    @Test
    public void shouldUpdateSubtaskStatusToNew() {
        Epic epic = addEpicTask();
        Subtask subtask = addSubTask(epic);

        manager.addEpicTask(epic);
        manager.addSubTask(subtask);
        subtask.setStatus(Status.NEW);
        manager.updateSubtaskStatus(subtask);

        assertEquals(Status.NEW, manager.getSubTaskById(subtask.getId()).getStatus());
        assertEquals(Status.NEW, manager.getEpicById(epic.getId()).getStatus());
    }

    @Test
    public void shouldUpdateSubtaskStatusToInProgress() {
        Epic epic = addEpicTask();
        Subtask subtask = addSubTask(epic);

        manager.addEpicTask(epic);
        manager.addSubTask(subtask);
        subtask.setStatus(Status.IN_PROGRESS);
        manager.updateSubtaskStatus(subtask);

        assertEquals(Status.IN_PROGRESS, manager.getSubTaskById(subtask.getId()).getStatus());
        assertEquals(Status.IN_PROGRESS, manager.getEpicById(epic.getId()).getStatus());
    }

    @Test
    public void shouldUpdateSubtaskStatusToDone() {
        Epic epic = addEpicTask();
        Subtask subtask = addSubTask(epic);

        manager.addEpicTask(epic);
        manager.addSubTask(subtask);
        subtask.setStatus(Status.DONE);
        manager.updateSubtaskStatus(subtask);

        assertEquals(Status.DONE, manager.getSubTaskById(subtask.getId()).getStatus());
        assertEquals(Status.DONE, manager.getEpicById(epic.getId()).getStatus());
    }

    @Test
    public void shouldNotUpdateTaskIfNull() {
        Task task = addTask();

        manager.addTask(task);
        manager.updateTaskStatus(null);

        assertEquals(task, manager.getTaskById(task.getId()));
    }

    @Test
    public void shouldNotUpdateEpicIfNull() {
        Epic epic = addEpicTask();

        manager.addEpicTask(epic);
        manager.updateEpicStatus(null);

        assertEquals(epic, manager.getEpicById(epic.getId()));
    }

    @Test
    public void shouldNotUpdateSubtaskIfNull() {
        Epic epic = addEpicTask();
        Subtask subtask = addSubTask(epic);

        manager.addEpicTask(epic);
        manager.addSubTask(subtask);
        manager.updateSubtaskStatus(null);

        assertEquals(subtask, manager.getSubTaskById(subtask.getId()));
    }

    @Test
    public void shouldDeleteAllTasks() {
        Task task = addTask();

        manager.addTask(task);
        manager.clearTask();

        assertEquals(Collections.EMPTY_LIST, manager.getTask());
    }

    @Test
    public void shouldDeleteAllEpics() {
        Epic epic = addEpicTask();

        manager.addEpicTask(epic);
        manager.clearEpic();

        assertEquals(Collections.EMPTY_LIST, manager.getEpicTask());
    }

    @Test
    public void shouldDeleteAllSubtasks() {
        Epic epic = addEpicTask();
        Subtask subtask = addSubTask(epic);

        manager.addEpicTask(epic);
        manager.addSubTask(subtask);
        manager.clearSubTask();

        assertTrue(epic.getSubtaskListId().isEmpty());
        assertTrue(manager.getSubTask().isEmpty());
    }

    @Test
    public void shouldChangeTask() {
        Task task = addTask();
        Task task1 = new Task(1, "MyTask 323", "Description", Status.IN_PROGRESS, LocalDateTime.of(2020, 9, 19, 14, 1), 10);

        manager.addTask(task);
        manager.changeTask(task1);
        List<Task> tasks = manager.getTask();

        assertNotNull(task.getStatus());
        assertEquals(Status.IN_PROGRESS, task1.getStatus());
        assertEquals(List.of(task1), tasks);
    }

    @Test
    public void shouldDeleteAll() {
        Task task = addTask();
        Epic epic = addEpicTask();
        Subtask subtask = addSubTask(epic);

        manager.addEpicTask(epic);
        manager.addSubTask(subtask);
        manager.addTask(task);
        manager.clearAll();

        assertEquals(Collections.EMPTY_LIST, manager.getSubTask());
        assertEquals(Collections.EMPTY_LIST, manager.getTask());
        assertEquals(Collections.EMPTY_LIST, manager.getEpicTask());
    }

    @Test
    public void shouldDeleteTaskById() {
        Task task = addTask();

        manager.addTask(task);
        manager.delTask(task.getId());

        assertEquals(Collections.EMPTY_LIST, manager.getTask());
    }

    @Test
    public void shouldDeleteEpicById() {
        Epic epic = addEpicTask();

        manager.addEpicTask(epic);
        manager.delEpic(epic.getId());

        assertEquals(Collections.EMPTY_LIST, manager.getEpicTask());
    }

    @Test
    public void shouldDeleteSubtaskById() {
        Epic epic = addEpicTask();
        Subtask subtask = addSubTask(epic);

        manager.addEpicTask(epic);
        manager.addSubTask(subtask);
        manager.delSubTask(subtask.getId());

        assertEquals(Collections.EMPTY_LIST, manager.getSubTask());
    }

    @Test
    public void shouldReturnEmptyListTasksIfNoTasks() {
        assertTrue(manager.getTask().isEmpty());
    }

    @Test
    public void shouldReturnEmptyListEpicsIfNoEpics() {
        assertTrue(manager.getEpicTask().isEmpty());
    }

    @Test
    public void shouldReturnEmptyListSubtasksIfNoSubtasks() {
        assertTrue(manager.getSubTask().isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenGetSubtaskByEpicIdIsEmpty() {
        Epic epic = addEpicTask();

        manager.addEpicTask(epic);
        List<Subtask> subtasks = manager.getSubtaskListEpics(epic.getId());

        assertTrue(subtasks.isEmpty());
    }

    @Test
    public void shouldReturnNullIfTaskDoesNotExist() {
        assertNull(manager.getTaskById(123));
    }

    @Test
    public void shouldReturnNullIfEpicDoesNotExist() {
        assertNull(manager.getEpicById(123));
    }

    @Test
    public void shouldReturnNullIfSubtaskDoesNotExist() {
        assertNull(manager.getSubTaskById(123));
    }

    @Test
    public void shouldReturnEmptyHistory() {
        assertEquals(Collections.EMPTY_LIST, manager.getHistory());
    }

    @Test
    public void shouldReturnEmptyHistoryIfTasksNotExist() {
        manager.getTaskById(123);
        manager.getSubTaskById(321);
        manager.getEpicById(132);

        assertTrue(manager.getHistory().isEmpty());
    }

    @Test
    public void shouldReturnHistoryWithTasks() {
        Epic epic = addEpicTask();
        Subtask subtask = addSubTask(epic);

        manager.addEpicTask(epic);
        manager.addSubTask(subtask);
        manager.getEpicById(epic.getId());
        manager.getSubTaskById(subtask.getId());
        List<Task> list = manager.getHistory();

        assertEquals(2, list.size());
        assertTrue(list.contains(subtask));
        assertTrue(list.contains(epic));
    }
}