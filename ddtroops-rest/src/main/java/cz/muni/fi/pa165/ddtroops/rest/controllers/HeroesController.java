package cz.muni.fi.pa165.ddtroops.rest.controllers;

import java.util.Collection;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.muni.fi.pa165.ddtroops.dto.HeroDTO;
import cz.muni.fi.pa165.ddtroops.facade.HeroFacade;
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

        logger.debug("rest create()");

        try {
            return heroFacade.create(hero);
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new ResourceAlreadyExistingException();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final HeroDTO updateHero(@RequestBody HeroDTO hero) throws Exception {

        logger.debug("rest updateHero()");

        try {
            return heroFacade.update(hero);
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new ResourceAlreadyExistingException();
        }
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<HeroDTO> list() throws JsonProcessingException {
        logger.debug("rest getHeroes()");
        return heroFacade.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final HeroDTO get(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getHero({})", id);
        HeroDTO heroDTO = heroFacade.findById(id);
        if (heroDTO == null){
            throw new ResourceNotFoundException();
        }
        return heroDTO;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<HeroDTO> delete(@PathVariable("id") long id) throws Exception {
        logger.debug("rest deleteHero({})", id);
        HeroDTO heroDTO = heroFacade.findById(id);
        if (heroDTO == null){
            throw new ResourceNotFoundException();
        }
        heroFacade.delete(id);
        return heroFacade.findAll();
    }
}
