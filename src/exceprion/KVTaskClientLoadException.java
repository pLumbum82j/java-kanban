package exceprion;

public class KVTaskClientLoadException extends RuntimeException{
    public KVTaskClientLoadException(String s, Throwable exception) {
        System.out.println(s);
        exception.printStackTrace();
    }
}
