package cz.fi.muni.pa165.ddtroops.dao.impl;

import cz.fi.muni.pa165.ddtroops.dao.TroopDao;
import cz.fi.muni.pa165.ddtroops.entity.Troop;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import java.util.*;
import javax.persistence.*;

/**
 *
 * @author xgono
 */
@Repository
@Transactional
public class TroopDaoImpl implements TroopDao {
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Troop create(Troop troop) {
        if(troop == null) {
            throw new IllegalArgumentException("troop");
        }
        em.persist(troop);
        return troop;
    }

    @Override
    public Troop update(Troop troop) {
        if(troop == null) {
            throw new IllegalArgumentException("troop");
        }
        em.merge(troop);
        return troop;
    }

    @Override
    public Troop delete(Troop troop) {
        if(troop == null) {
            throw new IllegalArgumentException("troop");
        }
        em.remove(em.contains(troop) ? troop : em.merge(troop));
        return troop;
    }


    public Troop findById(long id) {
        return em.find(Troop.class, id);
    }

    @Override
    public List<Troop> listAll() {
        return em.createQuery("select t from Troop t", Troop.class).getResultList();
    }
    
    @Override
    public Troop findByName(String name) {
        if(name == null) {
            throw new IllegalArgumentException("name");
        }
        try {
            return em
                    .createQuery("select t from Troop t where name = :name",
                            Troop.class).setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
