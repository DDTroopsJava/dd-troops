package cz.fi.muni.pa165.ddtroops.service;

import cz.fi.muni.pa165.ddtroops.entity.User;
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
    void registerUser(User u, String unencryptedPassword);

    /**
     * Get all registered users
     */
    List<User> listAll();

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
