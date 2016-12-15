package cz.muni.fi.pa165.ddtroops.mvc.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by pstanko.
 * @author pstanko
 */
public class TroopCreateDTOValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
