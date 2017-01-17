package cz.muni.fi.pa165.ddtroops.mvc.tools;

import java.beans.PropertyEditorSupport;

import cz.muni.fi.pa165.ddtroops.dto.RoleDTO;
import cz.muni.fi.pa165.ddtroops.facade.RoleFacade;

/**
 * Created by Peter Zaoral.
 *
 * @author Peter Zaoral
 */

/**
 * Converts a String to a Role (when submitting form)
 */
    public class RoleEditor extends PropertyEditorSupport {

    private RoleFacade roleFacade;

    public RoleEditor(RoleFacade roleFacade) {
        this.roleFacade = roleFacade;
    }

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
