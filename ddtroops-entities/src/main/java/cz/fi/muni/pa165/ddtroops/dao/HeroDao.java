package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.entity.Hero;

import java.util.List;

/**
 * Created by pstanko.
 *
 * @author pstanko
 */
public interface HeroDao {
    /**
     * Find hero by id
     * @param id - User ID
     * @return instance of user with given ID, null if not exists
     */
    public Hero findById(Long id);

    /**
     * Persist hero into database
     * @param hero - instance of hero
     */
    public Hero create(Hero hero);

    /**
     * Updates hero in database
     * @param hero - instance of hero
     */
    public Hero update(Hero hero);

    /**
     * Delete hero from database
     * @param hero - instance of hero
     */
    public Hero delete(Hero hero);

    /**
     * List all heroes
     * @return List of heroes, null if none
     */
    public List<Hero> findAll();

    /**
     * Find hero by its name
     * @param name - Hero name
     * @return hero if exists, null if none
     */
    public Hero findByName(String name);
}
