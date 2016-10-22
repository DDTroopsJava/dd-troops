package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.entity.Hero;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by pstanko.
 *
 * @author pstanko
 */

@Repository
@Transactional
public class HeroDaoImpl implements HeroDao {


    @Override
    public Hero findById(Long id) {
        return null;
    }

    @Override
    public Hero create(Hero hero) {
        return null;
    }

    @Override
    public Hero update(Hero hero) {
        return null;
    }

    @Override
    public Hero delete(Hero hero) {
        return null;
    }

    @Override
    public List<Hero> findAll() {
        return null;
    }

    @Override
    public Hero findByName(String name) {
        return null;
    }
}
