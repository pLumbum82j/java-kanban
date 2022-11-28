package task;

import java.util.Objects;

public class Subtask extends Task{
    private int epicId;

    public Subtask(int epicId, String  name, String description, Status status) {
        super( name, description, status);
        this.epicId = epicId;
    }
    public Subtask(int epicId, int id, String  name, String description, Status status) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public Subtask(int epicId, String  name, String description) {
        super( name, description);
        this.epicId = epicId;

    }


    @Override
    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }


    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s,%s\n", id, TaskType.SUBTASK, status, name, description, getEpicId());
    }
}
