package cz.fi.muni.pa165.ddtroops.service.exceptions;

import cz.fi.muni.pa165.ddtroops.exceptions.DDTroopsServiceException;

/**
 * Created by pstanko.
 * @author pstanko
 */
public class InvallidPasswordException extends DDTroopsServiceException {
    public InvallidPasswordException() {
    }

    public InvallidPasswordException(String s) {
        super(s);
    }

    public InvallidPasswordException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InvallidPasswordException(Throwable throwable) {
        super(throwable);
    }

    public InvallidPasswordException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
