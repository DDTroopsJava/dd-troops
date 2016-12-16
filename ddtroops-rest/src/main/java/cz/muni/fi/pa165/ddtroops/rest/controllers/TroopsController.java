/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.ddtroops.rest.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import cz.muni.fi.pa165.ddtroops.dto.TroopCreateDTO;
import cz.muni.fi.pa165.ddtroops.dto.TroopDTO;
import cz.muni.fi.pa165.ddtroops.dto.TroopUpdateDTO;
import cz.muni.fi.pa165.ddtroops.facade.TroopFacade;
import cz.muni.fi.pa165.ddtroops.rest.exceptions.ResourceAlreadyExistingException;
import cz.muni.fi.pa165.ddtroops.rest.exceptions.ResourceNotFoundException;
import cz.muni.fi.pa165.ddtroops.rest.exceptions.ResourceNotModifiedException;
import cz.muni.fi.pa165.ddtroops.rest.tools.ApiUris;
import java.util.Collection;
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
 *
 * @author Richard
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_ORDERS)
public class TroopsController {
    
    private final static Logger logger = LoggerFactory.getLogger(TroopsController.class);

    @Autowired
    private TroopFacade troopFacade;
    
    /**
     * Returns all troops according to a Summary View
     *
     * @return list of al TroopDTOs
     * @throws JsonProcessingException
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final Collection<TroopDTO> getTroops() throws JsonProcessingException {
        logger.debug("rest getTroops()");
        return troopFacade.findAll();
    }
    
    /**
     *
     * Returns a troop with the given id
     *
     * @param id troop identifier
     * @return TroopDTO
     * @throws Exception - ResourceNotFoundException if no such Troop was found
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final TroopDTO getTroop(@PathVariable("id") long id) throws Exception {
        logger.debug("rest getTroop({})", id);
        TroopDTO troopDTO = troopFacade.findById(id);
        if (troopDTO == null){
            throw new ResourceNotFoundException();
        }
        return troopDTO;
    }
    
    /**
     * Deletes the troop with the given id
     * 
     * @param id id of the troop to be deleted
     * @throws Exception - ResourceNotFoundException if no Troop was found
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteTroop(@PathVariable("id") long id) throws Exception {
        logger.debug("rest deleteTroop({})", id);
        try {
            troopFacade.delete(id);
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new ResourceNotFoundException();
        }
    }
    
    /**
     * Creates a Troop with the parameters of the given DTO
     * 
     * @param troop DTO of the Troop to be created
     * @return DTO of the newly created Troop
     * @throws Exception - ResourceAlreadyExistingException if such a troop already exists
     */
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final TroopDTO createTroop(@RequestBody TroopCreateDTO troop) throws Exception {

        logger.debug("rest createTroop()");

        try {
            return troopFacade.create(troop);
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new ResourceAlreadyExistingException();
        }
    }

    /**
     * Updates a Troop with the parameters of the given DTO
     * 
     * @param troop - DTO of the troop to be updated, with the new values
     * @return DTO of the newly updated Troop
     * @throws Exception - ResourceNotModifiedException if the Troop could not be updated 
     */
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public final TroopDTO updateTroop(@RequestBody TroopUpdateDTO troop) throws Exception {

        logger.debug("rest updateTroop()");

        try {
            return troopFacade.update(troop);
        } catch (Exception ex) {
            logger.warn(ex.getMessage());
            throw new ResourceNotModifiedException();
        }
    }
}
