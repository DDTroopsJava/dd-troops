package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.entity.Troop;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TroopDaoImpl implements TroopDao {
    @Override
    public Troop create(Troop troop) {
        return null;
    }

    @Override
    public Troop update(Troop troop) {
        return null;
    }

    @Override
    public Troop delete(Troop troop) {
        return null;
    }

    @Override
    public Troop getByName(String troop) {
        return null;
    }

    @Override
    public Troop getById(long id) {
        return null;
    }

    @Override
    public List<Troop> listAll() {
        return null;
    }
}
