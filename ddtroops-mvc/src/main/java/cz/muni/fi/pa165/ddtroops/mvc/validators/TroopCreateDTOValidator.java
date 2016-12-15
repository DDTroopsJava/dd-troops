package cz.muni.fi.pa165.ddtroops.mvc.validators;

import cz.muni.fi.pa165.ddtroops.dto.TroopCreateDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by pstanko.
 * @author pstanko
 */
public class TroopCreateDTOValidator implements Validator {
     @Override
    public boolean supports(Class<?> aClass) {
        return TroopCreateDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TroopCreateDTO troop = (TroopCreateDTO) o;

        if(troop.getName().isEmpty()){
            errors.rejectValue("name", "TroopDTO.name.length", "Troop name should not be empty");
        }
        
        if(troop.getMission().isEmpty()){
            errors.rejectValue("mission", "TroopDTO.mission.length", "Mission should not be empty");
        }
        
        if(troop.getGold() < 0){
            errors.rejectValue("gold", "TroopDTO.gold", "Gold ammount can't be negative");
        }
    }
}
