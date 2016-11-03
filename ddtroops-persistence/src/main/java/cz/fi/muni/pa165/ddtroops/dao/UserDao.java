package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by pstanko.
 *
 * @author pstanko
 */
public interface UserDao extends JpaRepository<User, Long>
{
    User findByEmail(String email);
}
