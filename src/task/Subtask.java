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


    /**
     * "Метод получения ID Epic'a у Subtask'a"
     * @return ID Epic'a
     */
    public int getEpicId() {
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

    /**
     * "Метод преобразования информации из строки в задачу"
     * @param value Полученная строка из файла tasks.csv
     * @return Результат преобразования строки в задачу
     */
    public static Subtask fromString(String value){
        String[] input = value.split(",");
        Subtask subtask = new Subtask(Integer.parseInt(input[5]), input[3], input[4], Status.valueOf(input[2]));
        subtask.setId(Integer.valueOf(input[0]));
        return subtask;
    }

}
