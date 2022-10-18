package manager.history;

import task.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    public final ArrayList<Task> history = new ArrayList<>();

    @Override
    public ArrayList<Task> getHistory() {
        if (history.size() > 10) {
            for (int i = history.size(); i > 10; i--) {
                history.remove(history.size() - i);
            }
        }
        return history;
    }

    @Override
    public void add(Task task) {
        history.add(task);
    }
}
