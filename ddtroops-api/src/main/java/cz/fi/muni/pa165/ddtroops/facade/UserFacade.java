package cz.fi.muni.pa165.ddtroops.facade;

import java.util.Collection;

import cz.fi.muni.pa165.ddtroops.dto.UserDTO;

/**
 * Created by pstanko.
 *
 * @author pstanko
 */
public interface UserFacade {
    /**
     * Gets user by id
     * @param userId
     * @return null if not exists
     */
    UserDTO findById(Long userId);

    /**
     *
     * @param userDTO
     * @return
     */
    boolean delete(UserDTO userDTO);

    /**
     * Gets user by email
     * @param email
     * @return
     */
    UserDTO findByEmail(String email);

    /**
     * Updates existing user
     * @param u
     * @return
     */
    UserDTO update(UserDTO u);

    /**
     * Updates user password
     * @param u
     * @param oldPassword
     * @param newPassword
     * @return
     */
    UserDTO updatePassword(UserDTO u, String oldPassword, String newPassword);

    /**
     * Register new user
     * @param user
     * @param unencryptedPassword
     */
    void register(UserDTO user, String unencryptedPassword);

    /**
     * Get all registered users
     * @return Collection of users
     */
    Collection<UserDTO> findAll();

    /**
     * Auhentificate user
     * @param email
     * @param password
     * @return
     */
    boolean authenticate(String email, String password);

    /**
     * Check whether the given user is admin.
     */
    boolean isAdmin(UserDTO u);
}
