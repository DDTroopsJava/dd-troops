package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.entity.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;

/**
 * Created by pstanko.
 *
 * @author P. Kolacek
 */
@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {
    
    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Role create(Role role)
    {
        em.persist(role);
        return role;
    }

    @Override
    public Role delete(Role role)
    {
        em.remove(em.contains(role) ? role : em.merge(role));
        return role;
    }

    @Override
    public Role update(Role role)
    {
        role = em.merge(role);
        return role;
    }

    @Override
    public Role findById(long id)
    {
        return em.find(Role.class, id);
    }
    
    @Override
    public Role findByName(String name)
    {
        try{
            return em.createQuery("select r from Role r where name = :name", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    @Override
    public List<Role> listAll()
    {
        return em.createQuery("select r from Role r", Role.class)
                .getResultList();
    }
}
