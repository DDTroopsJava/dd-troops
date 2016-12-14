package cz.muni.fi.pa165.ddtroops.rest.controllers;

import java.util.Collection;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.muni.fi.pa165.ddtroops.dto.UserCreateDTO;
import cz.muni.fi.pa165.ddtroops.dto.UserDTO;
import cz.muni.fi.pa165.ddtroops.dto.UserUpdateDTO;
import cz.muni.fi.pa165.ddtroops.facade.UserFacade;
import cz.muni.fi.pa165.ddtroops.rest.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.ddtroops.rest.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.ddtroops.rest.tools.ApiUris;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pstanko.
 * @author pstanko
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_USERS)
public class UsersController {

    private final static Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UserFacade userFacade;

    /**
     * returns all users according to a Summary View
     *
     * @return list of UserDTOs
     * @throws JsonProcessingException
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<UserDTO> getUsers() throws JsonProcessingException {
        logger.debug("rest getUsers()");
        return userFacade.findAll();
    }

    /**
     *
     * getting user according to id
     *
     * @param id user identifier
     * @return UserDTO
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDTO getUser(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getUser({})", id);
        UserDTO userDTO = userFacade.findById(id);
        if (userDTO == null){
            throw new ResourceNotFoundException();
        }
        return userDTO;


    }



    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteProduct(@PathVariable("id") long id) throws Exception {
        logger.debug("rest deleteUser({})", id);
        try {
            userFacade.delete(id);
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new ResourceNotFoundException();
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDTO createUser(@RequestBody UserCreateDTO user) throws Exception {

        logger.debug("rest createUser()");

        try {
            return userFacade.register(user, user.getPassword());
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new ResourceAlreadyExistingException();
        }
    }



    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDTO updateUser(@RequestBody UserUpdateDTO user) throws Exception {

        logger.debug("rest updateUser()");

        try {
            return userFacade.update(user);
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new ResourceAlreadyExistingException();
        }
    }
}
