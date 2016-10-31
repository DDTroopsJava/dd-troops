package cz.fi.muni.pa165.ddtroops.dao.impl;

import cz.fi.muni.pa165.ddtroops.dao.RoleDao;
import cz.fi.muni.pa165.ddtroops.entity.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;

/**
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
        if(role == null) {
            throw new IllegalArgumentException("role");
        }
        em.persist(role);
        return role;
    }

    @Override
    public Role delete(Role role)
    {
        if(role == null) {
            throw new IllegalArgumentException("role");
        }
        em.remove(em.contains(role) ? role : em.merge(role));
        return role;
    }

    @Override
    public Role update(Role role)
    {
        if(role == null) {
            throw new IllegalArgumentException("role");
        }
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
        if(name == null) {
            throw new IllegalArgumentException("name");
        }
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
