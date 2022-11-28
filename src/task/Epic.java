package task;

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


    public String toString() {
        return String.format("%d,%s,%s,%s,%s\n", id, TaskType.EPIC, status, name, description);
    }

    /**
     * "Метод преобразования информации из строки в задачу"
     * @param value Полученная строка из файла tasks.csv
     * @return Результат преобразования строки в задачу
     */
    public static Epic fromString(String value) {
        String[] input = value.split(",");
        Epic epic = new Epic(input[3], input[4], Status.valueOf(input[2]));
        epic.setId(Integer.valueOf(input[0]));
        return epic;
    }
}
