package cz.muni.fi.pa165.ddtroops.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by pstanko.
 * @author pstanko
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason="You are not authorized for this action!")
public class NotAuthorizedException extends RuntimeException {
}
