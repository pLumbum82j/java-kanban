package task;

public class Subtask extends Task{
    private int epicId;

    public Subtask(int epicId, String  name, String description, String status) {
        super( name, description, status);
        this.epicId = epicId;
    }

  //  public Subtask(int id, String name, String description, String status) {
 //       super(id, name, description, status);
 //   }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
