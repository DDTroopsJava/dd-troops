package cz.muni.fi.pa165.ddtroops.facade;

import java.util.List;
import java.util.Set;

import cz.muni.fi.pa165.ddtroops.dto.TroopCreateDTO;
import cz.muni.fi.pa165.ddtroops.dto.TroopDTO;
import cz.muni.fi.pa165.ddtroops.dto.TroopUpdateDTO;

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
    TroopDTO update(TroopUpdateDTO troopDTO);

    /**
     * Creates a troop with the parameters of the given DTO
     * 
     * @param troopCreateDTO
     * @return DTO of the newly created Troop
     */
    TroopDTO create(TroopCreateDTO troopCreateDTO);
    
    /**
     * Removes the given HeroDTO from the given TroopDTO
     * 
     * @param troopId
     * @param heroId
     * @return DTO of the updated Troop
     */
    TroopDTO removeHero(long troopId, long heroId);

    /**
     *
     * @param troopId
     * @param heroId
     * @return
     */
    TroopDTO addHero(long troopId, long heroId);

    /**
     *
     */

    Set<TroopDTO> allHeroes(long TroopId);

    /**
     * Deletes the given troop
     *
     * @param id
     */
    void delete(Long id);

    /**
     * Battle between troops
     *
     * @param troop1DTO
     * @param troop2DTO
     * @return TroopDTO that won
     */
    TroopDTO battle(TroopDTO troop1DTO, TroopDTO troop2DTO);

    /**
     * Get topN troops
     *
     * @param n first troops
     * @param mission name
     * @param troopSize
     * @return List of first n troops
     */
    List<TroopDTO> topN(int n, String mission, Long troopSize);
}
