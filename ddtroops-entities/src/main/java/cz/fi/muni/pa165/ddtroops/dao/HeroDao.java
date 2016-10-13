package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.entity.Hero;

import java.util.List;

/**
 * Created by pstanko.
 *
 * @author pstanko
 */
public interface HeroDao {
    public Hero findById(Long id);
    public void create(Hero c);
    public void delete(Hero c);
    public List<Hero> findAll();
    public Hero findByName(String name);
}
