package cz.muni.fi.pa165.ddtroops.mvc.tools;

import cz.muni.fi.pa165.ddtroops.dto.RoleDTO;
import cz.muni.fi.pa165.ddtroops.facade.RoleFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * Created by Peter Zaoral.
 *
 * @author Peter Zaoral
 */

/**
 * Converts a String to a Role (when submitting form)
 */
@Component
public class RoleEditor extends PropertyEditorSupport {

    @Autowired
    private RoleFacade roleFacade;

    @Override
    public void setAsText(String text) {
        Long id = Long.parseLong(text);
        RoleDTO role = this.roleFacade.findById(id);
        this.setValue(role);
    }

    @Override
    public String getAsText() {
        return super.getAsText();
    }
}
