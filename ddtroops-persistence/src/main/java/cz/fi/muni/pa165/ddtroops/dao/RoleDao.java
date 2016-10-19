package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.entity.Role;

import java.util.List;

public interface RoleDao {

    Role create(Role group);
    Role delete(Role group);
    Role update(Role group);
    Role getById(long id);
    List<Role> listAll();
}
