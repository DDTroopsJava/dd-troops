package cz.fi.muni.pa165.ddtroops.service.services.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cz.fi.muni.pa165.ddtroops.dao.TroopDao;
import cz.fi.muni.pa165.ddtroops.entity.Troop;
import cz.fi.muni.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
import cz.fi.muni.pa165.ddtroops.service.services.TroopService;
import java.util.Comparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        } catch (Throwable ex) {
            throw new DDTroopsServiceException("Cannot find troop with " + troopId + " id.", ex);
        }
    }

    @Override
    public Troop findByName(String name) throws DDTroopsServiceException {
        try {
            return troopDao.findByName(name);
        } catch (Throwable ex) {
            throw new DDTroopsServiceException("Cannot find troop with name " + name, ex);
        }
    }

    @Override
    public List<Troop> findAll() throws DDTroopsServiceException {
        try {
            return troopDao.findAll();
        } catch (Throwable ex) {
            throw new DDTroopsServiceException("Could not receive a list of all troops!", ex);
        }
    }

    @Override
    public void update(Troop t) throws DDTroopsServiceException {
        try {
            troopDao.save(t);
        } catch (Throwable ex) {
            throw new DDTroopsServiceException(
                "Cannot update troop with id: " + t.getId() + " and name: " + t.getName(), ex);
        }
    }

    @Override
    public void delete(Troop t) throws DDTroopsServiceException {
        try {
            troopDao.delete(t);
        } catch (Throwable ex) {
            throw new DDTroopsServiceException(
                "Cannot delete troop with id: " + t.getId() + " and name: " + t.getName(), ex);
        }
    }

    @Override
    public Troop battle(Troop t1, Troop t2) throws DDTroopsServiceException {

        long t1AttacksT2 = t2.getDefensePower() - t1.getAttackPower();
        long t2AttacksT1 = t1.getDefensePower() - t2.getAttackPower();

        if (t1AttacksT2 == t2AttacksT1) {
            return null;
        } else if (t1AttacksT2 < t2AttacksT1) {
            t1.levelUpHeroes();
            return t1;
        }

        t2.levelUpHeroes();
        return t2;

    }

    @Override
    public List<Troop> topN(int n, String mission, Long troopSize) throws DDTroopsServiceException {

        Stream<Troop> stream;

        try {
            if (mission != null && troopSize != null) {
                stream = troopDao.findByMission(mission).stream().filter(t -> t.size() == troopSize);

            } else if (mission != null) {
                stream = troopDao.findByMission(mission).stream();
            } else if (troopSize != null) {
                stream = troopDao.findAll().stream().filter(t -> t.size() == troopSize);
            } else {
                stream = troopDao.findAll().stream();
            }

            Stream<Troop> sortedStream = stream.sorted(Comparator.comparing(Troop::getAttackPower).reversed());
            List<Troop> sortedList = sortedStream
                .limit(n)
                .collect(Collectors.toList());
            return sortedList;
        } catch (IllegalArgumentException ex) {
            throw new DDTroopsServiceException("Can't return Top N if N is negative!");
        }

    }
}
