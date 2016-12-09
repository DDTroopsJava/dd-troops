package cz.muni.fi.pa165.ddtroops.rest.controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import cz.muni.fi.pa165.ddtroops.dto.UserDTO;
import cz.muni.fi.pa165.ddtroops.dto.UserLoginDTO;
import cz.muni.fi.pa165.ddtroops.facade.UserFacade;
import cz.muni.fi.pa165.ddtroops.rest.exceptions.NotAuthorizedException;
import cz.muni.fi.pa165.ddtroops.rest.tools.ApiUris;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pstanko.
 * @author pstanko
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_AUTH)
public class AuthController {

    private final static Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UserFacade userFacade;

    @RequestMapping(value = "/login",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final UserDTO login(@RequestBody UserLoginDTO user, HttpServletResponse response) throws Exception {

        logger.debug("rest loginUser()");

        try {
            if (userFacade.authenticate(user.getEmail(), user.getPassword())){
                UserDTO userDTO = userFacade.findByEmail(user.getEmail());
                response.addCookie( new Cookie("userId", userDTO.getId().toString()));
                return userDTO;
            }
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new NotAuthorizedException();
        }
        throw new NotAuthorizedException();
    }
}
