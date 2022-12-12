package tets;

import manager.history.HistoryManager;
import manager.history.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;
import task.Status;
import task.Task;

import java.time.Instant;
import java.time.LocalDateTime;

public class InMemoryHistoryManagerTest {
    HistoryManager manager;
    private int id = 0;

    public int generateId() {
        return ++id;
    }

    protected Task addTask() {
        return new Task("Description", "Title", Status.NEW, LocalDateTime.now(), 0);
    }

    @BeforeEach
    public void beforeEach() {
        manager = new InMemoryHistoryManager();
    }

}
