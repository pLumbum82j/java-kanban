package manager.history;

import task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {


    /**
     * Класс "узлов" в котором расположен Task, ссылка на предыдущую Nod'у и следующую Nod'у
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
        if (nodes.containsKey(task.getId())) {
            changeNode(task.getId());
        }
        linkLast(task);
        nodes.put(task.getId(), tail);
    }

    @Override
    public void remove(int id) {
        changeNode(id);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    /**
     * "Изменение состояния Nod'ы "
     *
     * @param id Узла, по которому требуется изменения
     */
    private void changeNode(int id) {
        Node node = nodes.remove(id);
        if (node != null) {
            final Node next = node.next;
            final Node prev = node.prev;
            if (head.equals(node) && tail.equals(node)) {
                head = null;
                tail = null;
            } else if (head.equals(node)) {
                head = next;
                head.prev = null;
            } else if (tail.equals(node)) {
                tail = prev;
                tail.next = null;
            } else {
                prev.next = next;
                next.prev = prev;
            }
            node.value = null;
        }
    }

    /**
     * "Добавление Task'и в конец мапы nodes"
     *
     * @param task Задача
     */
    private void linkLast(Task task) {
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
     *
     * @return Список задач
     */
    private List<Task> getTasks() {
        List<Task> history = new ArrayList<>();
        Node currentNode = head;
        while (currentNode != null) {
            history.add(currentNode.value);
            currentNode = currentNode.next;
        }
        return history;
    }
}