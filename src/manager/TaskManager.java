package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private int generatorId = 1;
    //Это пункт 1 задачи ТЗ
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();


    //Это пунк 2 задачи ТЗ
// 2.1 Посмотреть все task
    public ArrayList<Task> getTask() {
        return new ArrayList<>(tasks.values());
    }

    //Сделать аналогичню локину, как выше на сабтаск и эпики с выводом списка всех задач
    public ArrayList<Task> getEpicTask() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Task> getSubTask() {
        return new ArrayList<>(subtasks.values());
    }
    // 2.2 Сделать удаление всех задач через удаление (очищение мапы)

    // 2.3 Получение по идентификатору - поиск в мапе по ID, забрать и вывести (ретюрнуть)

    // 2.4 Создание тасков
    public void addTask(Task task) {
        task.setId(generatorId);
        tasks.put(task.getId(), task);
        generatorId++;
    }

    public void addSubTask(Subtask subtask) {
        subtask.setId(generatorId);
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }
        subtask.setId(generatorId);
        subtasks.put(subtask.getId(), subtask);
        epic.addSubTaskId(subtask.getId());

        updateEpicStatus(epic);
        //Логика

        generatorId++;
    }

    public void addEpicTask(Epic epic) {
        epic.setId(generatorId);
        epics.put(epic.getId(), epic);
        generatorId++;
    }
// 2.5 Обновление очень похоже на создание, к нам пришел новый вариант Таски и мы просто заменяем её.
// Выполнить предварительно проверку имеется ли такая таска
    // Не забыть вызвать метод updateEpicStatus(epic); при обновлении сабтаска

    // 2.6 В метод по удалению приходит ID и собственно удалям
    // Не забыть вызвать метод updateEpicStatus(epic); при удалении сабтаска
    public void delTask(int id) {

        for (Task tasksid : tasks.values()){
            System.out.println(tasksid);
            if (id == tasksid.getId()) {
         //       tasks.clear();
            }
        }
    }


    //3.1 метод будет получать на вход ID Эпика, будем забирать из мапы Эпиков необходимый эпик,
    // далее забирать список подзадач и возвращать наружу

    //4 Управление статусами задач
    private void updateEpicStatus(Epic epic) {
        ArrayList<Integer> subtaskListId = epic.getSubtaskListId();
        if (subtaskListId.isEmpty()) {
            epic.setStatus("NEW");
            return;
        }
        String status = null;
        for (int subtaskId : subtaskListId) {
            Subtask subtask = subtasks.get(subtaskId);

            if (status == null) {
                status = subtask.getStatus();
                continue;
            }
            if (status.equals(subtask.getStatus())) {
                continue;
            }
            epic.setStatus("IN PROGRESS");
            return;
        }
        epic.setStatus(status);
    }
}
