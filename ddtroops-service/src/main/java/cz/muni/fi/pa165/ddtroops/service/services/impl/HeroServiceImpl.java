package cz.muni.fi.pa165.ddtroops.service.services.impl;

import cz.muni.fi.pa165.ddtroops.dao.HeroDao;
import cz.muni.fi.pa165.ddtroops.dao.RoleDao;
import cz.muni.fi.pa165.ddtroops.entity.Hero;
import cz.muni.fi.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
import cz.muni.fi.pa165.ddtroops.service.services.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Peter Zaoral.
 *
 * @author Peter Zaoral.
 */
@Service
public class HeroServiceImpl implements HeroService {
    @Autowired
    private HeroDao heroDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public Hero createHero(Hero hero) throws DDTroopsServiceException {
        if (hero == null) {
            throw new IllegalArgumentException("Hero is null.");
        }
        try {
            hero.getRoles().forEach(r -> {r.addHero(hero); roleDao.save(r);});
            Hero save = heroDao.save(hero);
            return save;
        } catch (Throwable e) {
            throw new DDTroopsServiceException("Cannot create hero named " + hero.getName() + " with id" + hero.getId(), e);
        }
    }

    @Override
    public Hero findById(Long id) throws DDTroopsServiceException {
        if (id == null) {
            throw new IllegalArgumentException("Hero id is null.");
        }
        try {
            return heroDao.findOne(id);
        } catch (Throwable e) {
            throw new DDTroopsServiceException("Cannot find hero with id  " + id, e);
        }
    }

    @Override
    public Hero findByName(String name) throws DDTroopsServiceException {
        if ((name == null) || (name.isEmpty())) {
            throw new IllegalArgumentException("Hero name is empty or null.");
        }
        try {
            return heroDao.findByName(name);
        } catch (Throwable e) {
            throw new DDTroopsServiceException("Cannot find hero named  " + name, e);
        }
    }

    @Override
    public List<Hero> findAll() throws DDTroopsServiceException {
        try {
            return heroDao.findAll();
        } catch (Throwable e) {
            throw new DDTroopsServiceException("Could not receive list of heroes", e);
        }
    }

    @Override
    public Hero updateHero(Hero hero) throws DDTroopsServiceException {
        if (hero == null) {
            throw new IllegalArgumentException("Hero is null.");
        }
        try {
            hero.getRoles().forEach(r -> {r.addHero(hero); roleDao.save(r);});
            return heroDao.save(hero);
        } catch (Throwable e) {
            throw new DDTroopsServiceException("Cannot update hero named " + hero.getName()
                    + " with id" + hero.getId(), e);
        }
    }

    @Override
    public void deleteHero(Hero hero) throws DDTroopsServiceException {
        if (hero == null) {
            throw new IllegalArgumentException("Hero is null.");
        }
        try {
            heroDao.delete(hero);
        } catch (Throwable e) {
            throw new DDTroopsServiceException("Cannot deleteAll hero named " + hero.getName()
                    + " with id" + hero.getId(), e);
        }
    }

    @Override
    public Boolean deleteAllHeroes() throws DDTroopsServiceException {
        try {
            heroDao.deleteAll();
            return true;
        } catch (Throwable e) {
            throw new DDTroopsServiceException("Cannot deleteAll all heroes.", e);
        }
    }
}
