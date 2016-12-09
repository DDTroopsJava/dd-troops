package cz.muni.fi.pa165.ddtroops.dao;

import cz.muni.fi.pa165.ddtroops.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by pstanko.
 *
 * @author pstanko
 */
public interface UserDao extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
