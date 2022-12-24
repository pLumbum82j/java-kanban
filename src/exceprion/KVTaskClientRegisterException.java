package exceprion;

/**
 * "Класс собственного исключения при работе с регистрацией"
 */
public class KVTaskClientRegisterException extends RuntimeException {
    public KVTaskClientRegisterException(Throwable exception) {
        exception.printStackTrace();
    }
}