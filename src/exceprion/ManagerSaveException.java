package exceprion;

/**
 * "Класс собственного исключения"
 */
public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(final String message) {
        super(message);
    }
}