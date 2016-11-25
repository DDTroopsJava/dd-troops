package cz.fi.muni.pa165.ddtroops.facade;

import cz.fi.muni.pa165.ddtroops.dto.TroopDTO;

import java.util.List;

/**
 * @author xgono
 */
public interface TroopFacade {

    /**
     * Returns the troop with the given ID
     *
     * @param troopId
     * @return null if troop doesn't exist
     */
    TroopDTO findById(Long troopId);

    /**
     * Returns the troop with the given name
     *
     * @param name
     * @return null if troop doesn't exist
     */
    TroopDTO findByName(String name);

    /**
     * Returns a list of all troops
     *
     * @return list of all troops
     */
    List<TroopDTO> findAll();

    /**
     * Updates the given troop
     *
     * @param troopDTO
     * @return
     */
    TroopDTO update(TroopDTO troopDTO);

    /**
     * Deletes the given troop
     *
     * @param id
     */
    void delete(Long id);
}
