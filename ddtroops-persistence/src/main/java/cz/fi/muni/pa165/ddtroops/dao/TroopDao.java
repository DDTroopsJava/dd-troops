package cz.fi.muni.pa165.ddtroops.dao;


import cz.fi.muni.pa165.ddtroops.entity.*;
import java.util.*;

/**
 * Created by xgono
 * 
 * @author xgono
 */
public interface TroopDao {
    
    /**
     * Persist given troop into DB
     * @param troop - instance of troop to be persisted
     */
    Troop create(Troop troop);
    
    /**
     * Update given troop in DB
     * @param troop - instance of troop to be updated
     */
    Troop update(Troop troop);
    
    /**
     * Deletes given troop from DB
     * @param troop - instance of troop to be deleted
     */
    Troop delete(Troop troop);
    
    /**
     * Returns the troop from DB with the given ID
     * @param id - ID of the troop to be retrieved from DB
     * @return troop with given ID if it exists, null if not
     */
    Troop findById(long id);
    
    /**
     * List all troops
     * @return List of all troops from DB, null if none found
     */
    List<Troop> findAll();
    
    /**
     * Returns the troop from DB with the given name
     * @param name - name of the troop to be retrieved from DB
     * @return troop with given name if it exists, null if not
     */
    Troop findByName(String name);
}
