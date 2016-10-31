package cz.fi.muni.pa165.ddtroops.dao.impl;

import cz.fi.muni.pa165.ddtroops.dao.HeroDao;
import cz.fi.muni.pa165.ddtroops.entity.Hero;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Peter Zaoral.
 *
 * @author P. Zaoral
 */

@Repository
@Transactional
public class HeroDaoImpl implements HeroDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Hero findById(Long id) {
        return em.find(Hero.class, id);
    }

    @Override
    public Hero create(Hero hero) {
        if(hero == null) {
            throw new IllegalArgumentException("hero");
        }
        em.persist(hero);
        return hero;
    }

    @Override
    public Hero update(Hero hero) {
        if(hero == null) {
            throw new IllegalArgumentException("hero");
        }
        hero = em.merge(hero);
        return hero;
    }

    @Override
    public Hero delete(Hero hero) {
        em.remove(em.contains(hero) ? hero : em.merge(hero));
        return hero;
    }

    @Override
    public List<Hero> listAll() {

        return em.createQuery("select h from Hero h", Hero.class)
                .getResultList();
    }

    @Override
    public Hero findByName(String name) {
        if(name == null) {
            throw new IllegalArgumentException("name");
        }
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
