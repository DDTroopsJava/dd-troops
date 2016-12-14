package cz.muni.fi.pa165.ddtroops.service.services;

import cz.muni.fi.pa165.ddtroops.entity.User;
import cz.muni.fi.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pstanko
 */
@Service
public interface UserService {

    /**
     * Register the given user with the given unencrypted password.
     */
    User register(User u, String unencryptedPassword) throws DDTroopsServiceException;

    /**
     * Update the given user but cannot change unencrypted password!
     *
     * @param u User that should be updated
     * @throws DDTroopsServiceException When any error occurs - constraints are not met or something
     */
    User update(User u) throws DDTroopsServiceException;

    /**
     * Removes user
     *
     * @param u - user
     * @return return true if successfully deleted
     * @throws DDTroopsServiceException
     */
    boolean delete(User u) throws DDTroopsServiceException;

    /**
     * Update user password
     *
     * @param u           - for given user
     * @param oldPassword - old password (unencrypted)
     * @param newPassword - new password (unencrypted)
     * @return true if changed, false if not
     * @throws DDTroopsServiceException When any error occurs
     */
    boolean updatePassword(User u, String oldPassword, String newPassword) throws DDTroopsServiceException;

    /**
     * Get all reg. users
     *
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
     *
     * @param userId User id
     * @return null if not exits
     * @throws DDTroopsServiceException when something went wrong
     */
    User findById(Long userId) throws DDTroopsServiceException;

    /**
     * Find user by email
     *
     * @param email - User email
     * @return User or null if not found
     * @throws DDTroopsServiceException
     */
    User findByEmail(String email) throws DDTroopsServiceException;
}
