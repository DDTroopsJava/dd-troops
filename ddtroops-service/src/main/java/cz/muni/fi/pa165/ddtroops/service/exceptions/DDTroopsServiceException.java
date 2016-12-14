package cz.muni.fi.pa165.ddtroops.service.exceptions;

import org.springframework.dao.DataAccessException;

/**
 * Created by pstanko.
 *
 * @author pstanko
 */
public class DDTroopsServiceException extends DataAccessException {


    public DDTroopsServiceException(String msg) {
        super(msg);
    }

    public DDTroopsServiceException() {
        super("Unknown data access error.");
    }

    public DDTroopsServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
