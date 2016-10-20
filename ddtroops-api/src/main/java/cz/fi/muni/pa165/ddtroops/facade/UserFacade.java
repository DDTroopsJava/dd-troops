package cz.fi.muni.pa165.ddtroops.facade;

import cz.fi.muni.pa165.ddtroops.dto.UserDTO;

import java.util.Collection;

/**
 * Created by pstanko.
 *
 * @author pstanko
 */
public interface UserFacade {
    UserDTO findById(Long userId);

    UserDTO findByEmail(String email);

    UserDTO update(UserDTO u);

    /**
     * Register the given user with the given unencrypted password.
     */
    void register(UserDTO u, String unencryptedPassword);

    /**
     * Get all registered users
     */
    Collection<UserDTO> getAllUsers();


    /**
     * Check if the given user is admin.
     */
    boolean isAdmin(UserDTO u);
}
