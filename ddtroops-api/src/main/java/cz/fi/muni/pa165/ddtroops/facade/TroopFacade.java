package cz.fi.muni.pa165.ddtroops.facade;

import cz.fi.muni.pa165.ddtroops.dto.TroopDTO;
import java.util.List;

/**
 *
 * @author xgono
 */
public interface TroopFacade {
    
    /**
     * Returns the troop with the given ID
     * @param troopId
     * @return 
     */
    TroopDTO findById(Long troopId);
    
    /**
     * Returns the troop with the given name
     * @param name
     * @return 
     */
    TroopDTO findByName(String name);
    
    /**
     * Returns a list of all troops
     * @return 
     */
    List<TroopDTO> findAll();
    
    /**
     * Updates the given troop
     * @param troopDTO
     * @return 
     */
    TroopDTO update(TroopDTO troopDTO);
    
    /**
     * Deletes the given troop
     * @param troopDTO
     */
    void delete(TroopDTO troopDTO);
}
