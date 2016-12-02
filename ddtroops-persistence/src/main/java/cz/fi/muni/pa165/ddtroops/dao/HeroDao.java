package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.entity.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Peter Zaoral.
 *
 * @author P. Zaoral
 */
public interface HeroDao extends JpaRepository<Hero, Long> {
    /**
     * Find hero by its name
     *
     * @param name - Hero name
     * @return hero if exists, null if none
     */
    public Hero findByName(String name);
}
