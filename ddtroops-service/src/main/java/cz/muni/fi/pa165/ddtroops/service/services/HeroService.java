package cz.muni.fi.pa165.ddtroops.service.services;

/**
 * Created by Peter on 21.11.2016.
 */

import java.util.List;
import java.util.Set;

import cz.muni.fi.pa165.ddtroops.entity.Hero;
import cz.muni.fi.pa165.ddtroops.entity.Role;
import cz.muni.fi.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
import org.springframework.stereotype.Service;

/**
 * Created by Peter Zaoral.
 *
 * @author Peter Zaoral.
 */
@Service
public interface HeroService {
    /**
     * Creates hero
     *
     * @param hero hero that will be created
     * @return created hero with id
     */
    Hero create(Hero hero) throws DDTroopsServiceException;

    /**
     * Gets hero with unique id
     *
     * @param id hero id
     * @return hero wuth particular unique id
     */
    Hero findById(Long id) throws DDTroopsServiceException;

    /**
     * Gets hero with unique name.
     *
     * @param name hero name
     * @return hero with particular unique name
     */
    Hero findByName(String name) throws DDTroopsServiceException;

    /**
     * Gets all heroes
     *
     * @return collection of heroes
     */
    List<Hero> findAll() throws DDTroopsServiceException;

    /**
     * Updates existing hero
     *
     * @param hero hero that will be updated
     */
    Hero updateHero(Hero hero) throws DDTroopsServiceException;

    /**
     * Removes particular hero
     *
     * @param hero hero that will be deleted
     */
    void deleteHero(Hero hero) throws DDTroopsServiceException;

    /**
     * Delete all heroes
     *
     * @return list of deleted heroes
     */
    Boolean deleteAllHeroes() throws DDTroopsServiceException;

    /**
     * Adds role to hero
     * @param heroId Hero id
     * @param roleId Role id
     * @return updated Hero
     */
    Hero addRole(long heroId, long roleId);

    /**
     * Removes role from hero
     * @param heroId Hero Id
     * @param roleId Role Id
     * @return updated Hero
     */
    Hero removeRole(long heroId, long roleId);

    /**
     * Get all roles for hero
     * @param heroId hero id
     * @return
     */
    Set<Role> getRoles(long heroId);
}

