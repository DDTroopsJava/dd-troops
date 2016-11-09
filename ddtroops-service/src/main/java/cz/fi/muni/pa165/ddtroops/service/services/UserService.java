package cz.fi.muni.pa165.ddtroops.service.services;

import java.util.List;

import cz.fi.muni.pa165.ddtroops.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author pstanko
 */
@Service
public interface UserService {

    /**
     * Register the given user with the given unencrypted password.
     */
    void register(User u, String unencryptedPassword);

    void update(User u);

    boolean updatePassword(User u, String oldPassword, String newPassword);

    /**
     * Get all registered users
     */
    List<User> findAll();

    /**
     * Try to authenticate a user. Return true only if the hashed password matches the records.
     */
    boolean authenticate(User u, String password);

    /**
     * Check if the given user is admin.
     */
    boolean isAdmin(User u);

    User findById(Long userId);

    User findByEmail(String email);
}
