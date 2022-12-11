package tets;

import manager.FileBackedTasksManager;
import manager.InMemoryTaskManager;
import manager.Managers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBackedTasksManagerTest extends TaskManagerTest<InMemoryTaskManager> {
        public static final Path path = Path.of("data.test.csv");
        File file = new File(String.valueOf(path));
        @BeforeEach
        public void beforeEach() {
            manager = new FileBackedTasksManager(file);
        }

        @AfterEach
        public void afterEach() {
            try {
                Files.delete(path);
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        }

    }
