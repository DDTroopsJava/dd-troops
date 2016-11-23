package cz.fi.muni.pa165.ddtroops.service.exceptions;

/**
 * Created by pstanko.
 * @author pstanko
 */
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
    }

    public InvalidPasswordException(String s) {
        super(s);
    }

    public InvalidPasswordException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InvalidPasswordException(Throwable throwable) {
        super(throwable);
    }

    public InvalidPasswordException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
