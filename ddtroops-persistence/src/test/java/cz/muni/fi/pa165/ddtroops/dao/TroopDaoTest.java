package cz.muni.fi.pa165.ddtroops.dao;

import static org.junit.Assert.*;
import static org.testng.AssertJUnit.assertEquals;

import java.util.List;

import cz.muni.fi.pa165.ddtroops.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.ddtroops.entity.Hero;
import cz.muni.fi.pa165.ddtroops.entity.Troop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author pstanko
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class TroopDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TroopDao troopDao;

    @Autowired
    private HeroDao heroDao;

    private Troop troop1;
    private Troop troop2;
    private Troop troop3;

    private Hero hero1;
    private Hero hero2;
    private Hero hero3;
    private Hero unassignedHero;

    private static Hero createHero(String name) {
        Hero hero = new Hero();
        hero.setName(name);
        hero.setLevel(1000);
        return hero;
    }

    private static Troop createTroop(String name, String mission, int gold) {
        Troop troop = new Troop();
        troop.setName(name);
        troop.setMission(mission);
        troop.setGold(gold);
        return troop;
    }

    @BeforeMethod
    public void createToops() throws Exception {
        hero1 = createHero("Kunigunda");
        hero2 = createHero("Shakira");
        hero3 = createHero("Chuck Norris");
        unassignedHero = createHero("The Rock");

        hero1 = heroDao.save(hero1);
        Assert.assertTrue(heroDao.findAll().contains(hero1));

        hero2 = heroDao.save(hero2);
        Assert.assertTrue(heroDao.findAll().contains(hero2));

        hero3 = heroDao.save(hero3);
        Assert.assertTrue(heroDao.findAll().contains(hero3));
        unassignedHero = heroDao.save(unassignedHero);
        Assert.assertTrue(heroDao.findAll().contains(unassignedHero));


        troop1 = createTroop("Troop1", "Lol", 1000);
        troop2 = createTroop("Troop2", "Lol", 1500);
        troop3 = createTroop("Troop3", "Heroic", 2000);

        troop1 = troopDao.save(troop1);
        Assert.assertTrue(troopDao.findAll().contains(troop1));
        troop2 = troopDao.save(troop2);
        Assert.assertTrue(troopDao.findAll().contains(troop2));
        troop3 = troopDao.save(troop3);
        Assert.assertTrue(troopDao.findAll().contains(troop3));

        troop1.addHero(hero1);
        troop2.addHero(hero2);
        troop3.addHero(hero3);


    }

    @Test
    public void shouldCreateNewTroop() throws Exception {
        Troop troop = createTroop("NewTroop", "Heroic", 2500);
        Troop created = troopDao.save(troop);

        assertEquals(troopDao.findAll().size(), 4);
        assertTrue(troopDao.findAll().contains(created));
        assertEquals(troopDao.findOne(created.getId()), troop);
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void shouldNotCreateExistingTroop() throws Exception {
        Troop troop = createTroop("Troop1", "Heroic", 2500);
        Troop created = troopDao.save(troop);

        assertEquals(troopDao.findAll().size(), 3);
        assertTrue(troopDao.findAll().contains(created));
        assertEquals(troopDao.findOne(created.getId()), troop);
    }

    @Test
    public void shouldUpdateTroop2() throws Exception {
        Troop troop = troopDao.findOne(troop2.getId());
        troop.setGold(0);
        troop.setMission("New Mission");
        troopDao.save(troop);

        assertTrue(troopDao.findAll().contains(troop));
        Troop updatedTroop = troopDao.findOne(troop.getId());
        assertEquals(updatedTroop, troop);
        assertEquals(updatedTroop.getGold(), 0);
        assertEquals(updatedTroop.getMission(), "New Mission");
    }

    @Test
    public void shouldDeleteTroop() throws Exception {
        List<Troop> allBeforeDelete = troopDao.findAll();
        assertEquals(allBeforeDelete.size(), 3);

        troop3.getHeroes().forEach(hero -> troop3.removeHero(hero));
        troop3 = troopDao.save(troop3);

        troopDao.delete(troop3);

        List<Troop> allAfterDelete = troopDao.findAll();
        Hero resultHero = heroDao.findOne(hero3.getId());

        assertEquals(allAfterDelete.size(), 2);
        assertNull(resultHero.getTroop());
        assertTrue(allAfterDelete.contains(troop1));
        assertTrue(allAfterDelete.contains(troop2));
        assertFalse(allAfterDelete.contains(troop3));
    }

    @Test
    public void shouldGetByIdTroop1() throws Exception {
        Troop troop = troopDao.findOne(troop1.getId());
        assertEquals(troop1, troop);
        assertEquals(troop.getName(), "Troop1");
        assertEquals(troop.getMission(), "Lol");
        assertEquals(troop.getGold(), 1000L);
    }

    @Test
    public void shouldGetNullForNonExistingTroopId() throws Exception {
        Troop troop = troopDao.findOne(1000L);
        assertEquals(troop, null);
    }

    @Test
    public void shouldGetByNameTroop1() throws Exception {
        Troop troop = troopDao.findByName(troop1.getName());
        assertEquals(troop1, troop);
        assertEquals(troop.getName(), "Troop1");
        assertEquals(troop.getMission(), "Lol");
        assertEquals(troop.getGold(), 1000L);
    }

    @Test
    public void shouldGetNullForNonExistingTroopName() throws Exception {
        Troop troop = troopDao.findByName("Non existing name");
        assertEquals(troop, null);
    }

    @Test
    public void shouldAddHeroToTroop1() throws Exception {
        Troop troop = troopDao.findOne(troop1.getId());
        troop.addHero(unassignedHero);
        troopDao.save(troop);
        Troop resultTroop = troopDao.findOne(troop.getId());
        Hero resultHero = heroDao.findOne(unassignedHero.getId());

        assertTrue(resultTroop.getHeroes().contains(unassignedHero));
        assertTrue(resultTroop.getHeroes().contains(hero1));
        assertEquals(resultHero.getTroop(), resultTroop);
        assertEquals(hero1.getTroop(), resultTroop);
        assertEquals(resultTroop.getHeroes().size(), 2);
    }

    @Test
    public void shouldDeleteHeroFromTroop1() throws Exception {
        Troop troop = troopDao.findOne(troop1.getId());
        troop.addHero(unassignedHero);
        troop.removeHero(hero1);

        Troop resultTroop = troopDao.findOne(troop.getId());
        Hero resultHero = heroDao.findOne(unassignedHero.getId());
        Hero resultHero1 = heroDao.findOne(hero1.getId());

        assertTrue(resultTroop.getHeroes().contains(unassignedHero));
        assertEquals(resultHero.getTroop(), resultTroop);
        assertFalse(resultTroop.getHeroes().contains(hero1));
        assertNull(resultHero1.getTroop());
        assertEquals(resultTroop.getHeroes().size(), 1);
    }

}