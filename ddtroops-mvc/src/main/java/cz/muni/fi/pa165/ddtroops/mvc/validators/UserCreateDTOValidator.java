package cz.muni.fi.pa165.ddtroops.mvc.validators;

import cz.muni.fi.pa165.ddtroops.dto.UserCreateDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by pstanko.
 * @author pstanko
 */
public class UserCreateDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return UserCreateDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserCreateDTO user = (UserCreateDTO) o;

        if(!user.getPhone().matches("\\+?\\d+")){
            errors.rejectValue("phone", "UserDTO.phone.format", "Phone does not meet specified format!");
        }
        if(!user.getEmail().matches(".+@.+\\....?")){
            errors.rejectValue("email", "UserDTO.email.format", "Email does not meet specified format!");
        }
    }
}
