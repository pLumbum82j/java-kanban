package exceprion;

/**
 * "Класс собственного исключения при работе с выгрузкой информации из клиента"
 */
public class KVTaskClientLoadException extends RuntimeException {
    public KVTaskClientLoadException(String message, Throwable exception) {
        System.out.println(message);
        exception.printStackTrace();
    }
}
