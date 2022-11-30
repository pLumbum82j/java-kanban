package manager;


import manager.history.HistoryManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.HashMap;


/**
 * "Класс содержащий методы ответсвенные за перевод формата задачи в строки и из строк в задачи"
 */
public class CSVTaskFormat {

    /**
     * "Метод восстановления истории просмотра из файла"
     *
     * @param value          Строка из файла
     * @param tasks          Список Task
     * @param epics          Список Epic
     * @param subtasks       Список Subtask
     * @param defaultHistory Список истории
     */
    static void HistoryFromString(String value,
                                  HashMap<Integer, Task> tasks,
                                  HashMap<Integer, Epic> epics,
                                  HashMap<Integer, Subtask> subtasks,
                                  HistoryManager defaultHistory) {
        String[] splitString = value.split(",");
        for (int i = 0; i < splitString.length; i++) {
            int index = Integer.valueOf(splitString[i]);
            if (tasks.containsKey(index)) {
                defaultHistory.add(tasks.get(index));
            } else if (epics.containsKey(index)) {
                defaultHistory.add(epics.get(index));
            } else if (subtasks.containsKey(index)) {
                defaultHistory.add(subtasks.get(index));
            }
        }
    }

    /**
     * "Метод преобразования истории задач в строку"
     *
     * @param manager Список задач
     * @return Список ID задач через запятую
     */
    static String historyToString(HistoryManager manager) {
        String valueHistoryToString = "";
        for (int i = 0; i < manager.getHistory().size(); i++) {
            valueHistoryToString += manager.getHistory().get(i).getId() + " ";
        }
        // Не хотел через if, хотел показать, что умею и знаю методы trim и replace
        valueHistoryToString = valueHistoryToString.trim();
        valueHistoryToString = valueHistoryToString.replace(' ', ',');
        return valueHistoryToString;
    }

    /**
     * "Метод заполнения менеджера задачами по типу задачи"
     *
     * @param generatorID ID Задачи
     * @param input       Задача
     * @param tasks       Список Task
     * @param epics       Список Epic
     * @param subtasks    Список Subtask
     * @return ID задачи
     */
    static int fillingTasks(int generatorID,
                            String input,
                            HashMap<Integer, Task> tasks,
                            HashMap<Integer, Epic> epics,
                            HashMap<Integer, Subtask> subtasks) {
        String[] splitLine = input.split(",");

        switch (splitLine[1]) {
            case "TASK":
                Task task = Task.fromString(input);
                tasks.put(task.getId(), task);
                if (generatorID < task.getId())
                    generatorID = task.getId();
                break;
            case "EPIC":
                Epic epic = Epic.fromString(input);
                epics.put(epic.getId(), epic);
                if (generatorID < epic.getId())
                    generatorID = epic.getId();
                break;
            case "SUBTASK":
                Subtask subTask = Subtask.fromString(input);
                subtasks.put(subTask.getId(), subTask);
                if (generatorID < subTask.getId())
                    generatorID = subTask.getId();
                break;
        }
        return generatorID;
    }
}

