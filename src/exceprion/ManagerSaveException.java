package exceprion;

/**
 * "Класс собственного исключения при работе с файлами"
 */
public class ManagerSaveException extends RuntimeException {
    public ManagerSaveException(final String message) {
        super(message);
    }
}