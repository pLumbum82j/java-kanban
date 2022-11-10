package manager.history;

import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    /**
     * Класс "узлов" в котором расположен Task ссылка на предыдущую Node и следующую Node
     */
    static class Node {
        Task value;
        Node prev;
        Node next;

        public Node(Task value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * "Мапа узлов состоящая из ID Task'a и Nod'ы"
     */
    private final Map<Integer, Node> nodes = new HashMap<>();
    private Node head;
    private Node tail;


    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        remove(task.getId());
        linkLast(task);
        nodes.put(task.getId(), tail);
    }

    /**
     * "Изменение ссылок Nod'ы"
     * @param node Узел
     */
    public void changeNode(Node node) {
        if (node != null) {
            final Node next = node.next;
            final Node prev = node.prev;
            node.value = null;
            if (head == node && tail == node) {
                head = null;
                tail = null;
            } else if (head == node) {
                head = next;
                head.prev = null;
            } else if (tail == node) {
                tail = prev;
                tail.next = null;
            } else {
                prev.next = next;
                next.prev = prev;
            }
        }
    }

    @Override
    public void remove(int id) {
        changeNode(nodes.get(id));
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    /**
     * "Добавление Task'и в конец мапы nodes"
     * @param task Задача
     */
    public void linkLast(Task task) {
        final Node oldTail = tail;
        final Node newNode = new Node(task, oldTail, null);
        tail = newNode;
        nodes.put(task.getId(), newNode);
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
    }

    /**
     * "Метод получения истории просмотров"
     * @return Список задач
     */
    public List<Task> getTasks() {
        List<Task> history = new ArrayList<>();
        Node currentNode = head;
        while (currentNode != null) {
            history.add(currentNode.value);
            currentNode = currentNode.next;
        }
        return history;
    }
}