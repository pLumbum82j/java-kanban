package task;

import manager.Status;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    private ArrayList<Integer> subtaskListId;

    public Epic(String name, String description, Status status) {
        super(name, description, status);
        subtaskListId = new ArrayList<>();
    }

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
        subtaskListId = new ArrayList<>();
    }

    public Epic(String name, String description) {
        super(name, description);
        subtaskListId = new ArrayList<>();
    }

    public void addSubTaskId(int subtaskId) {
        subtaskListId.add(subtaskId);
    }

    public void removeSubTaskId(int subtaskId) {
        subtaskListId.remove((Integer) subtaskId);
    }

    public void clearSubTaskId() {
        subtaskListId.clear();
    }

    public ArrayList<Integer> getSubtaskListId() {
        return subtaskListId;
    }

    public void setSubtaskListId(ArrayList<Integer> subtaskListId) {
        this.subtaskListId = subtaskListId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Epic)) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return subtaskListId.equals(epic.subtaskListId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskListId);
    }
}
