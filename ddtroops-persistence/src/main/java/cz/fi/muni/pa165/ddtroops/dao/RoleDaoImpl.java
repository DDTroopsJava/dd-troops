package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.entity.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by pstanko.
 *
 * @author pstanko
 */
@Repository
@Transactional
public class RoleDaoImpl implements RoleDao {
    @Override
    public Role create(Role group) {
        return null;
    }

    @Override
    public Role delete(Role group) {
        return null;
    }

    @Override
    public Role update(Role group) {
        return null;
    }

    @Override
    public Role getById(long id) {
        return null;
    }

    @Override
    public List<Role> listAll() {
        return null;
    }
}
