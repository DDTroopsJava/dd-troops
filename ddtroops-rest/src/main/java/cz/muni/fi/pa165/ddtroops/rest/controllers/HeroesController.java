package cz.muni.fi.pa165.ddtroops.rest.controllers;

import cz.muni.fi.pa165.ddtroops.dto.HeroCreateDTO;
import cz.muni.fi.pa165.ddtroops.dto.HeroDTO;
import cz.muni.fi.pa165.ddtroops.dto.HeroUpdateDTO;
import cz.muni.fi.pa165.ddtroops.facade.HeroFacade;
import cz.muni.fi.pa165.ddtroops.rest.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.ddtroops.rest.tools.ApiUris;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Peter Zaoral
 *
 * @author Peter Zaoral.
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_HEROES)
public class HeroesController {
    private final static Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private HeroFacade heroFacade;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final HeroDTO createHero(@RequestBody HeroDTO hero) throws Exception { // POZOR

        logger.debug("rest createHero()");

        try {
            return heroFacade.create(hero);
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new ResourceAlreadyExistingException();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final HeroDTO updateUser(@RequestBody HeroUpdateDTO hero) throws Exception {

        logger.debug("rest updateUser()");

        try {
            return heroFacade.update(hero);
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new ResourceAlreadyExistingException();
        }
    }
}
