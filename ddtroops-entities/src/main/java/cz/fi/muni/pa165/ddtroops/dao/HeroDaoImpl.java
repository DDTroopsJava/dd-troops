package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.entity.Hero;
import org.springframework.stereotype.Repository;

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
public class HeroDaoImpl implements HeroDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Hero findById(Long id) {
        return em.find(Hero.class, id);
    }

    @Override
    public void create(Hero c) {
        em.persist(c);
    }

    @Override
    public void delete(Hero c) {
        em.remove(c);
    }

    @Override
    public List<Hero> findAll() {
        return em.createQuery("select h from Hero h", Hero.class)
                .getResultList();
    }

    @Override
    public Hero findByName(String name) {

        try {
            return em
                    .createQuery("select h from Hero h where name = :name",
                            Hero.class).setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException nrf) {
            return null;
        }
    }
}
