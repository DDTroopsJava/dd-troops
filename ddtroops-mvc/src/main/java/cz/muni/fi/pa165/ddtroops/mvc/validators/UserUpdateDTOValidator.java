package cz.muni.fi.pa165.ddtroops.mvc.validators;

import cz.muni.fi.pa165.ddtroops.dto.UserUpdateDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by pstanko.
 * @author pstanko
 */
public class UserUpdateDTOValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return UserUpdateDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserUpdateDTO user = (UserUpdateDTO) o;
        if (!user.getPhone().matches("\\+?\\d+")) {
            errors.rejectValue("phone", "UserDTO.phone.format", "Phone does not meet specified format!");
        }
        if (!user.getEmail().matches(".+@.+\\....?")) {
            errors.rejectValue("phone", "UserDTO.email.format", "Email does not meet specified format!");
        }
        if(user.getName().isEmpty()){
            errors.rejectValue("name", "UserDTO.name.length", "User name should not be empty");
        }

    }
}
