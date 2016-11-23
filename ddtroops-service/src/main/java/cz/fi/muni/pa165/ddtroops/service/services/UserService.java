package cz.fi.muni.pa165.ddtroops.service.services;

import java.util.List;

import cz.fi.muni.pa165.ddtroops.entity.User;
import cz.fi.muni.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
import org.springframework.stereotype.Service;

/**
 * @author pstanko
 */
@Service
public interface UserService {

    /**
     * Register the given user with the given unencrypted password.
     */
    void register(User u, String unencryptedPassword) throws DDTroopsServiceException;

    /**
     * Update the given user but cannot change unencrypted password!
     * @param u User that should be updated
     * @throws DDTroopsServiceException When any error occures - constraints are not met or something
     */
    void update(User u) throws DDTroopsServiceException;

    boolean updatePassword(User u, String oldPassword, String newPassword) throws DDTroopsServiceException;

    /**
     * Get all reg. users
     * @return List of registred users
     * @throws DDTroopsServiceException
     */
    List<User> findAll() throws DDTroopsServiceException;

    /**
     * Try to authenticate a user. Return true only if the hashed password matches the records.
     */
    boolean authenticate(User u, String password) throws DDTroopsServiceException;

    /**
     * Check if the given user is admin.
     */
    boolean isAdmin(User u) throws DDTroopsServiceException;

    /**
     * Find user by userId
     * @param userId User id
     * @return null if not exits
     * @throws DDTroopsServiceException when something went wrong
     */
    User findById(Long userId) throws DDTroopsServiceException;

    User findByEmail(String email) throws DDTroopsServiceException;
}
