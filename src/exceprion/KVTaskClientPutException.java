package exceprion;
/**
 * "Класс собственного исключения при добавлении информации"
 */
public class KVTaskClientPutException extends RuntimeException {
    public KVTaskClientPutException(String message, Throwable exception) {
        System.out.println(message);
        exception.printStackTrace();
    }
}
