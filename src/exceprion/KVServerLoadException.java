package exceprion;
/**
 * "Класс собственного исключения при работе с выгрузкой информации с KV сервера"
 */
public class KVServerLoadException extends RuntimeException {
    public KVServerLoadException(String message, Throwable exception) {
        System.out.println(message);
        exception.printStackTrace();
    }
}
