package cz.fi.muni.pa165.ddtroops.dao;


import cz.fi.muni.pa165.ddtroops.entity.Troop;

import java.util.List;

public interface TroopDao {
    Troop create(Troop troop);
    Troop update(Troop troop);
    Troop delete(Troop troop);
    Troop getByName(String troop);
    Troop getById(long id);
    List<Troop> listAll();

}
