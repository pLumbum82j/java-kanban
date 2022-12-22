package exceprion;

import java.io.IOException;

public class KVServerLoadException extends RuntimeException{
    public KVServerLoadException(String s, Throwable exception) {
        System.out.println(s);
        exception.printStackTrace();
    }
}
