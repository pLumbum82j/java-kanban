package exceprion;

/**
 * "Класс собственного исключения при работе с валидацией задач"
 */
public class ManagerValidateException extends RuntimeException {
    public ManagerValidateException(String message) {
        super(message);
    }
}


