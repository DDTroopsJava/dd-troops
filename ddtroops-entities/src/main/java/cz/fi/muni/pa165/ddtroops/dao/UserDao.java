package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.entity.User;

import java.util.List;

/**
 * Created by pstanko.
 *
 * @author pstanko
 */
public interface UserDao {
    /**
     * Find user by id
     * @param id - User ID
     * @return instance of user with given ID, null if not exists
     */
    public User findById(Long id);

    /**
     * Find user by its name
     * @param email - user name
     * @return  user if exists, null if none
     */
    public User findByEmail(String email);

    /**
     * Persist user into database
     * @param user - instance of user
     */
    public void create(User user);

    /**
     * Updates user in database
     * @param user - instance of user
     */
    public void update(User user);

    /**
     * Delete user from database
     * @param user - instance of user
     */
    public void delete(User user);

    /**
     * List all users
     * @return List of users, null if none
     */
    public List<User> findAll();


}
