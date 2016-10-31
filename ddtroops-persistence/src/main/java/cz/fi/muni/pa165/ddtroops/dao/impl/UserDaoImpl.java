package cz.fi.muni.pa165.ddtroops.dao.impl;

import cz.fi.muni.pa165.ddtroops.dao.UserDao;
import cz.fi.muni.pa165.ddtroops.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author pstanko
 */
@Repository
@Transactional
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public User create(User u) {
        if(u == null) {
            throw new IllegalArgumentException(User.class.getName());
        }
        em.persist(u);
        return u;
    }

    @Override
    public User update(User user) {
        if(user == null) {
            throw new IllegalArgumentException(User.class.getName());
        }
        user = em.merge(user);
        return user;
    }

    @Override
    public User delete(User user) {
        if(user == null) {
            throw new IllegalArgumentException(User.class.getName());
        }

        em.remove(em.contains(user) ? user : em.merge(user));
        return user;
    }

    @Override
    public User findByEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Cannot search for null e-mail");
        }

        try {
            return em
                    .createQuery("select u from Users u where email=:email",
                            User.class).setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> listAll() {
        TypedQuery<User> query = em.createQuery("SELECT u FROM Users u",
                User.class);
        return (List<User>) query.getResultList();
    }
}
