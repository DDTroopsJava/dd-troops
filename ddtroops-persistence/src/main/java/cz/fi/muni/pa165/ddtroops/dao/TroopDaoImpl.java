package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.entity.Troop;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import java.util.*;
import javax.persistence.*;

/**
 * Created by xgono
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
        em.persist(troop);
        return troop;
    }

    @Override
    public Troop update(Troop troop) {
        em.merge(troop);
        return troop;
    }

    @Override
    public Troop delete(Troop troop) {
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
