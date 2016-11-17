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
     *
     * @param userId
     * @return
     */
    UserDTO findById(Long userId);

    /**
     *
     * @param email
     * @return
     */
    UserDTO findByEmail(String email);

    /**
     *
     * @param u
     * @return
     */
    UserDTO update(UserDTO u);

    UserDTO updatePassowrd(UserDTO u, String oldPassword, String newPassword);

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

    boolean authenticate(String email, String password);

    /**
     * Check if the given user is admin.
     */
    boolean isAdmin(UserDTO u);
}
