package task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

public class Task {

    protected int id;
    protected String name;
    protected String description;
    protected Status status;
    protected LocalDateTime startTime;
    protected long duration;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.status = Status.NEW;
    }

    public Task(String name, String description, Status status, LocalDateTime startTime, long duration) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }
    public Task(int id, String name, String description, Status status, LocalDateTime startTime, long duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
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

    public Optional<LocalDateTime> getStartTime() {
        return Optional.ofNullable(startTime);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        if(startTime == null){
            return null;
        }
        return startTime.plusMinutes(duration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(description, task.description) && Objects.equals(name, task.name) &&
                status == task.status && Objects.equals(startTime, task.startTime) &&
                Objects.equals(duration, task.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status, startTime, duration);
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s,%s,%s\n", id, TaskType.TASK, status, name, description, startTime, duration);
    }

    /**
     * "Метод преобразования информации из строки в задачу"
     * @param value Полученная строка из файла tasks.csv
     * @return Результат преобразования строки в задачу
     */
    public static Task fromString(String value) {


        String[] input = value.split(",");
        if (input[5].equals("null")){
            Task task = new Task(input[3], input[4], Status.valueOf(input[2]));
            task.setId(Integer.valueOf(input[0]));
            return task;
        }
        Task task = new Task(input[3], input[4], Status.valueOf(input[2]), LocalDateTime.parse(input[5]), Long.parseLong(input[6]));
        task.setId(Integer.valueOf(input[0]));
        return task;
    }


}
