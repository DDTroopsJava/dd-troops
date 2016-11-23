package cz.fi.muni.pa165.ddtroops.service.services;

import java.util.List;

import cz.fi.muni.pa165.ddtroops.entity.Troop;

import cz.fi.muni.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
import org.springframework.stereotype.Service;

/**
 *
 * @author xgono
 */
@Service
public interface TroopService {
    
    /**
     * Returns the troop with the given ID
     * @param troopId ID of the troop to be found
     * @return null if no troop with the given ID exists
     * @throws DDTroopsServiceException when an error occures
     */
    Troop findById(Long troopId) throws DDTroopsServiceException;
    
    /**
     * Returns the troop with the given name
     * @param name of the troop to be found
     * @return null if no troop with the given name exists
     * @throws DDTroopsServiceException when an error occures
     */
    Troop findByName(String name) throws DDTroopsServiceException;
    
    /**
     * Returns a list of all troops
     * @return List of all troops
     * @throws DDTroopsServiceException when an error occures
     */
    List<Troop> findAll() throws DDTroopsServiceException;
    
    /**
     * Updates the given troop
     * @param t troop that will be updated
     * @throws DDTroopsServiceException when an error occures
     */
    void update(Troop t) throws DDTroopsServiceException;
    
    /**
     * Deletes the given troop
     * @param t troop that will be deleted
     * @throws DDTroopsServiceException when an error occures
     */
    void delete(Troop t) throws DDTroopsServiceException;

    Troop battle(Troop t1, Troop t2) throws DDTroopsServiceException;

    List<Troop> topN(int n, String mission, Long troopSize) throws DDTroopsServiceException;
}
