package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.entity.Group;

import java.util.List;


public interface GroupDao {
    Group create(Group group);
    Group delete(Group group);
    Group update(Group group);
    Group getById(long id);
    List<Group> listAll();

}
