package cz.fi.muni.pa165.ddtroops.service.services.impl;

import cz.fi.muni.pa165.ddtroops.dao.TroopDao;
import cz.fi.muni.pa165.ddtroops.entity.Troop;
import cz.fi.muni.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
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
    public Troop findById(Long troopId) throws DDTroopsServiceException {
        try {
            return troopDao.findOne(troopId);
        } catch(Throwable ex) {
            throw new DDTroopsServiceException("Cannot find troop with " + troopId + " id." ,ex);
        }
    }

    @Override
    public Troop findByName(String name) throws DDTroopsServiceException {
        try {
            return troopDao.findByName(name);
        } catch(Throwable ex) {
            throw new DDTroopsServiceException("Cannot find troop with name " + name ,ex);
        }
    }

    @Override
    public List<Troop> findAll() throws DDTroopsServiceException {
        try {
            return troopDao.findAll();
        } catch(Throwable ex) {
            throw new DDTroopsServiceException("Could not receive a list of all troops!",ex);
        }
    }

    @Override
    public void update(Troop t) throws DDTroopsServiceException {
        try {
            troopDao.save(t);
        } catch(Throwable ex) {
            throw new DDTroopsServiceException("Cannot update troop with id: " + t.getId() + " and name: " + t.getName() ,ex);
        }
    }

    @Override
    public void delete(Troop t) throws DDTroopsServiceException {
        try {
            troopDao.delete(t);
        } catch(Throwable ex) {
            throw new DDTroopsServiceException("Cannot delete troop with id: " + t.getId() + " and name: " + t.getName() ,ex);
        }
    }
}
