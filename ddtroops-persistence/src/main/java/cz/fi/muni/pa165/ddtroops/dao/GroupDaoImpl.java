package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.entity.Group;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class GroupDaoImpl implements GroupDao {
    @Override
    public Group create(Group group) {
        return null;
    }

    @Override
    public Group delete(Group group) {
        return null;
    }

    @Override
    public Group update(Group group) {
        return null;
    }

    @Override
    public Group getById(long id) {
        return null;
    }

    @Override
    public List<Group> listAll() {
        return null;
    }
}
