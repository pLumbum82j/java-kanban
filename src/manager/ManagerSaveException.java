package manager;

/**
 * "Класс собственного исключения"
 */
public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException() {
    }

    public ManagerSaveException(final String message) {
        super(message);
    }
}