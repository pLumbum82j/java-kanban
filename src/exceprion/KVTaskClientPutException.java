package exceprion;

public class KVTaskClientPutException extends RuntimeException{
    public KVTaskClientPutException(String s, Throwable exception) {
        System.out.println(s);
        exception.printStackTrace();
    }
}
