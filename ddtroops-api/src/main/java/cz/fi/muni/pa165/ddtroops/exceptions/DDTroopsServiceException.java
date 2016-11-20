package cz.fi.muni.pa165.ddtroops.exceptions;

/**
 * Created by pstanko.
 *
 * @author pstanko
 */
public class DDTroopsServiceException extends Exception {
    public DDTroopsServiceException() {
        super();
    }

    public DDTroopsServiceException(String message, Throwable cause,
                                 boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DDTroopsServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DDTroopsServiceException(String message) {
        super(message);
    }

    public DDTroopsServiceException(Throwable cause) {
        super(cause);
    }
}
