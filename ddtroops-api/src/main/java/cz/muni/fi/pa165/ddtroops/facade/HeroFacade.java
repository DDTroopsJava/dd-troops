package cz.muni.fi.pa165.ddtroops.facade;

import java.util.Collection;
import java.util.Set;

import cz.muni.fi.pa165.ddtroops.dto.HeroDTO;
import cz.muni.fi.pa165.ddtroops.dto.RoleDTO;

/**
 * Created by Peter Zaoral.
 *
 * @author Peter Zaoral.
 */
public interface HeroFacade {
    /**
     * Creates hero
     *
     * @param hero hero that will be created
     * @return created hero with id
     */
    HeroDTO create(HeroDTO hero);

    /**
     * Gets hero with unique id
     *
     * @param id hero id
     * @return hero wuth particular unique id
     */
    HeroDTO findById(long id);

    /**
     * Gets hero with unique name.
     *
     * @param name hero name
     * @return hero with particular unique name
     */
    HeroDTO findByName(String name);

    /**
     * Gets all heroes
     *
     * @return collection of heroes
     */
    Collection<HeroDTO> findAll();

    /**
     * Updates existing hero
     *
     * @param hero hero that will be updated
     * @return updated hero
     */
    HeroDTO update(HeroDTO hero);

    /**
     * Removes particular hero
     *
     * @param id hero that will be deleted
     * @return true if hero is deleted, false otherwise
     */
    Boolean delete(Long id);

    /**
     * Delete all heroes
     *
     * @return true if all heroes was deleted, false otherwise
     */
    Boolean deleteAll();

    HeroDTO removeRole(Long heroId, Long roleId);

    HeroDTO addRole(Long heroId, Long roleId);

    Set<RoleDTO> heroRoles(Long heroId);
}
