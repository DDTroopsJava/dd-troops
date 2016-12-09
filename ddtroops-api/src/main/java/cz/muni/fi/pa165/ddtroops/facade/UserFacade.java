package cz.muni.fi.pa165.ddtroops.facade;

import java.util.Collection;

import cz.muni.fi.pa165.ddtroops.dto.UserCreateDTO;
import cz.muni.fi.pa165.ddtroops.dto.UserDTO;
import cz.muni.fi.pa165.ddtroops.dto.UserUpdateDTO;
import cz.muni.fi.pa165.ddtroops.dto.UserUpdatePassDTO;

/**
 * Created by pstanko.
 *
 * @author pstanko
 */
public interface UserFacade {
    /**
     * Gets user by id
     *
     * @param userId
     * @return null if not exists
     */
    UserDTO findById(Long userId);

    /**
     * Delete hero
     * @param id
     * @return
     */
    boolean delete(Long id);

    /**
     * Gets user by email
     *
     * @param email
     * @return
     */
    UserDTO findByEmail(String email);

    /**
     * Updates existing user
     *
     * @param u Updated user
     * @return
     */
    UserDTO update(UserUpdateDTO u);

    /**
     * Updates user password
     *
     * @param u Updated user
     * @return UserDTO
     */
    UserDTO updatePassword(UserUpdatePassDTO u);

    /**
     * Register new user
     *
     * @param user User create DTO
     * @param unencryptedPassword Unencrypted password
     * @return UserDTO
     */
    UserDTO register(UserCreateDTO user, String unencryptedPassword);

    /**
     * Register new user
     * @param user User Create DTO
     * @return UserDTO
     */
    UserDTO register(UserCreateDTO user);

    /**
     * Get all registered users
     *
     * @return Collection of users
     */
    Collection<UserDTO> findAll();

    /**
     * Auhentificate user
     *
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
