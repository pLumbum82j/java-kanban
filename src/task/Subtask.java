package task;

import manager.Status;

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


    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

}
