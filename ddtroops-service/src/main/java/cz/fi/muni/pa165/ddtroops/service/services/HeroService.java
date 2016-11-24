package cz.fi.muni.pa165.ddtroops.service.services;

/**
 * Created by Peter on 21.11.2016.
 */

import cz.fi.muni.pa165.ddtroops.entity.Hero;
import cz.fi.muni.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @param hero  hero that will be created
     * @return  created hero with id
     */
    void createHero (Hero hero) throws DDTroopsServiceException;

    /**
     * Gets hero with unique id
     * @param id    hero id
     * @return  hero wuth particular unique id
     */
    Hero findById(Long id) throws DDTroopsServiceException;

    /**
     * Gets hero with unique name.
     * @param name  hero name
     * @return  hero with particular unique name
     */
   Hero findByName(String name) throws DDTroopsServiceException;

    /**
     * Gets all heroes
     * @return  collection of heroes
     */
    List<Hero> findAll() throws DDTroopsServiceException;

    /**
     * Updates existing hero
     * @param hero  hero that will be updated
     */
    void updateHero (Hero hero) throws DDTroopsServiceException;

    /**
     * Removes particular hero
     * @param hero hero that will be deleted
     */
    void deleteHero (Hero hero)  throws DDTroopsServiceException;

    /**
     * Delete all heroes
     *
     * @return list of deleted heroes
     */
    Boolean deleteAllHeroes()  throws DDTroopsServiceException;
}

