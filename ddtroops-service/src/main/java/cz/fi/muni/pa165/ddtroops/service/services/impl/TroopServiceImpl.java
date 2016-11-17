package cz.fi.muni.pa165.ddtroops.service.services.impl;

import cz.fi.muni.pa165.ddtroops.dao.TroopDao;
import cz.fi.muni.pa165.ddtroops.entity.Troop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.fi.muni.pa165.ddtroops.service.services.TroopService;
import java.util.List;

/**
 *
 * @author xgono
 */
@Service
public class TroopServiceImpl implements TroopService {
    
    @Autowired
    private TroopDao troopDao;

    @Override
    public Troop findById(Long troopId) {
        return troopDao.findOne(troopId);
    }

    @Override
    public Troop findByName(String name) {
        return troopDao.findByName(name);
    }

    @Override
    public List<Troop> findAll() {
        return troopDao.findAll();
    }

    @Override
    public void update(Troop t) {
        troopDao.save(t);
    }

    @Override
    public void delete(Troop t) {
        troopDao.delete(t);
    }
}
