package exceprion;
/**
 * "Класс собственного исключения при работе с выгрузкой информации с сервера"
 */
public class KVServerLoadException extends RuntimeException {
    public KVServerLoadException(String message, Throwable exception) {
        System.out.println(message);
        exception.printStackTrace();
    }
}
