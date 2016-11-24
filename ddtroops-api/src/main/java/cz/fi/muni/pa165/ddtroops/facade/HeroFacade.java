package cz.fi.muni.pa165.ddtroops.facade;

import cz.fi.muni.pa165.ddtroops.dto.HeroDTO;

import java.util.Collection;

/**
 * Created by Peter Zaoral.
 *
 * @author Peter Zaoral.
 */
public interface HeroFacade {
    /**
     * Creates hero
     *
     * @param hero  hero that will be created
     * @return  created hero with id
     */
    public HeroDTO createHero (HeroDTO hero);

    /**
     * Gets hero with unique id
     * @param id    hero id
     * @return  hero wuth particular unique id
     */
    public HeroDTO findById(long id);

    /**
     * Gets hero with unique name.
     * @param name  hero name
     * @return  hero with particular unique name
     */
    public HeroDTO findByName(String name);

    /**
     * Gets all heroes
     * @return  collection of heroes
     */
    public Collection<HeroDTO> findAll ();

    /**
     * Updates existing hero
     * @param hero  hero that will be updated
     * @return  updated hero
     */
    public HeroDTO updateHero (HeroDTO hero);

    /**
     * Removes particular hero
     * @param hero hero that will be deleted
     * @return  true if hero is deleted, false otherwise
     */
    public Boolean deleteHero (HeroDTO hero);

    /**
     * Delete all heroes
     *
     * @return true if all heroes was deleted, false otherwise
     */
    public Boolean deleteAllHeroes();
}
