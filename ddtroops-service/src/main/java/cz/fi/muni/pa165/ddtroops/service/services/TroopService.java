package cz.fi.muni.pa165.ddtroops.service.services;

import cz.fi.muni.pa165.ddtroops.entity.Troop;
import cz.fi.muni.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xgono
 */
@Service
public interface TroopService {

    /**
     * Returns the troop with the given ID
     *
     * @param troopId ID of the troop to be found
     * @return null if no troop with the given ID exists
     * @throws DDTroopsServiceException when an error occures
     */
    Troop findById(Long troopId) throws DDTroopsServiceException;

    /**
     * Returns the troop with the given name
     *
     * @param name of the troop to be found
     * @return null if no troop with the given name exists
     * @throws DDTroopsServiceException when an error occurs
     */
    Troop findByName(String name) throws DDTroopsServiceException;

    /**
     * Returns a list of all troops
     *
     * @return List of all troops
     * @throws DDTroopsServiceException when an error occurs
     */
    List<Troop> findAll() throws DDTroopsServiceException;

    /**
     * Updates the given troop
     *
     * @param t troop that will be updated
     * @throws DDTroopsServiceException when an error occurs
     */
    Troop update(Troop t) throws DDTroopsServiceException;

    /**
     * Creates the given troop
     *
     * @param t troop that will be updated
     * @throws DDTroopsServiceException when an error occurs
     */
    Troop create(Troop t) throws DDTroopsServiceException;

    /**
     * Deletes the given troop
     *
     * @param t troop that will be deleted
     * @throws DDTroopsServiceException when an error occurs
     */
    void delete(Troop t) throws DDTroopsServiceException;

    /**
     * Battle between troops
     * @param t1 One troop
     * @param t2 Second troop
     * @return Winner troop
     * @throws DDTroopsServiceException when any error occurs
     */
    Troop battle(Troop t1, Troop t2) throws DDTroopsServiceException;

    /**
     * Returs top N troops by specific criteria
     * @param n Number of troops
     * @param mission Mission name
     * @param troopSize Troop Size
     * @return Top n Troops list from best to least
     * @throws DDTroopsServiceException when any error occurs
     */
    List<Troop> topN(int n, String mission, Long troopSize) throws DDTroopsServiceException;
}
