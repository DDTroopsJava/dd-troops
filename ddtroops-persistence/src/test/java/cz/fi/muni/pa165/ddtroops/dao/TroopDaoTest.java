package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.ddtroops.entity.Troop;
import cz.fi.muni.pa165.ddtroops.entity.Hero;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

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

    @BeforeMethod
    public void createToops() throws Exception {
        hero1 = createHero("Kunigunda");
        hero2 = createHero("Shakira");
        hero3 = createHero("Chuck Norris");
        unassignedHero = createHero("The Rock");

        heroDao.save(hero1);
        Assert.assertTrue(heroDao.findAll().contains(hero1));

        heroDao.save(hero2);
        Assert.assertTrue(heroDao.findAll().contains(hero2));

        heroDao.save(hero3);
        Assert.assertTrue(heroDao.findAll().contains(hero3));
        heroDao.save(unassignedHero);
        Assert.assertTrue(heroDao.findAll().contains(unassignedHero));


        troop1 = createTroop("Troop1", "Lol", 1000);
        troop2 = createTroop("Troop2", "Lol", 1500);
        troop3 = createTroop("Troop3", "Heroic", 2000);

        troop1.addHero(hero1);
        troop2.addHero(hero2);
        troop3.addHero(hero3);

        troopDao.save(troop1);
        Assert.assertTrue(troopDao.findAll().contains(troop1));
        troopDao.save(troop2);
        Assert.assertTrue(troopDao.findAll().contains(troop2));
        troopDao.save(troop3);
        Assert.assertTrue(troopDao.findAll().contains(troop3));
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
        troopDao.delete(troop3);

        assertEquals(troopDao.findAll().size(), 2);
        assertTrue(troopDao.findAll().contains(troop1));
        assertTrue(troopDao.findAll().contains(troop2));
        assertFalse(troopDao.findAll().contains(troop3));
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
    public void shouldAddHeroToTroop1() throws Exception
    {
        Troop troop = troopDao.findOne(troop1.getId());
        troop.addHero(unassignedHero);
        troopDao.save(troop);
        Troop result = troopDao.findOne(troop.getId());
        assertTrue(result.getHeroes().contains(unassignedHero));
        assertTrue(result.getHeroes().contains(hero1));
        assertEquals(result.getHeroes().size(), 2);
    }

    private static Hero createHero(String name){
        Hero hero = new Hero();
        hero.setName(name);
        hero.setExperience(1000);
        return hero;
    }

    private static Troop createTroop(String name, String mission, int gold){
        Troop troop = new Troop();
        troop.setName(name);
        troop.setMission(mission);
        troop.setGold(gold);
        return troop;
    }

}