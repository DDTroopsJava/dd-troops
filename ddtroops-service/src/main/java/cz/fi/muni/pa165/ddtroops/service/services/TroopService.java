package cz.fi.muni.pa165.ddtroops.service.services;

import java.util.List;

import cz.fi.muni.pa165.ddtroops.entity.Troop;

import org.springframework.stereotype.Service;

/**
 *
 * @author xgono
 */
@Service
public interface TroopService {
    
    /**
     * Returns the troop with the given ID
     * @param troopId
     * @return 
     */
    Troop findById(Long troopId);
    
    /**
     * Returns the troop with the given name
     * @param name
     * @return 
     */
    Troop findByName(String name);
    
    /**
     * Returns a list of all troops
     * @return 
     */
    List<Troop> findAll();
    
    /**
     * Updates the given troop
     * @param t
     */
    void update(Troop t);
    
    /**
     * Deletes the given troop
     * @param t
     */
    void delete(Troop t);
}
