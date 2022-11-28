package task;

import java.util.Objects;

public class Task {

    protected int id;
    protected String name;
    protected String description;
    protected Status status;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status);
    }


    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s\n", id, TaskType.TASK, status, name, description);
    }

    /**
     * "Метод преобразования информации из строки в задачу"
     * @param value Полученная строка из файла tasks.csv
     * @return Результат преобразования строки в задачу
     */
    public static Task fromString(String value) {
        String[] input = value.split(",");
        Task task = new Task(input[3], input[4], Status.valueOf(input[2]));
        task.setId(Integer.valueOf(input[0]));
        return task;
    }


}
