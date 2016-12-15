package cz.muni.fi.pa165.ddtroops.mvc.tools;

import cz.muni.fi.pa165.ddtroops.entity.Role;
import cz.muni.fi.pa165.ddtroops.service.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * Created by Peter Zaoral.
 *
 * @author Peter Zaoral
 */

/**
 *  Converts a String to a Role (when submitting form)
 */
@Component
public class RoleEditor extends PropertyEditorSupport {
    @Autowired
    private RoleService roleService;

    @Override
    public void setAsText(String text) {
        Role role = this.roleService.findById(Long.valueOf(text));
    this.setValue(role);
    }

}
