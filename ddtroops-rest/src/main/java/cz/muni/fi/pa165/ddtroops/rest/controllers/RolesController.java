package cz.muni.fi.pa165.ddtroops.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.muni.fi.pa165.ddtroops.dto.RoleCreateDTO;
import cz.muni.fi.pa165.ddtroops.dto.RoleDTO;
import cz.muni.fi.pa165.ddtroops.dto.RoleUpdateDTO;
import cz.muni.fi.pa165.ddtroops.facade.RoleFacade;
import cz.muni.fi.pa165.ddtroops.rest.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.ddtroops.rest.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.ddtroops.rest.tools.ApiUris;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Petr Kolacek
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_ROLES)
public class RolesController {
    private final static Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private RoleFacade roleFacade;
    
    
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final RoleDTO createRole(@RequestBody RoleDTO role) throws Exception {
        logger.debug("rest createRole()");
        try {
            return roleFacade.create(role);
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new ResourceAlreadyExistingException();
        }
    }
    
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final RoleDTO updateRole(@RequestBody RoleUpdateDTO role) throws Exception {
        logger.debug("rest updateRole()");
        try {
            return roleFacade.update(role);
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new ResourceAlreadyExistingException();
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<RoleDTO> getRoles() throws JsonProcessingException {
        logger.debug("rest getRoles()");
        return roleFacade.findAll();
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final RoleDTO getRole(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getRole({})", id);
        RoleDTO roleDTO = roleFacade.findById(id);
        if (roleDTO == null){
            throw new ResourceNotFoundException();
        }
        return roleDTO;
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteRole(@PathVariable("id") long id) throws Exception {
        logger.debug("rest deleteRole({})", id);
        try {
            roleFacade.delete(id);
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new ResourceNotFoundException();
        }
    }
    
}
