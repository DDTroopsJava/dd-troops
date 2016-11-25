package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.ddtroops.entity.Hero;
import cz.fi.muni.pa165.ddtroops.entity.Role;
import cz.fi.muni.pa165.ddtroops.entity.Troop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Craeted by xgono
 * Test class for HeroDAO
 *
 * @author Richard
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class HeroDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private HeroDao heroDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private TroopDao troopDao;

    private Hero hero1;
    private Hero hero2;
    private Role role1;
    private Role role2;
    private Role unusedRole;
    private Troop troop;
    private Troop troop2;

    @BeforeMethod
    public void setUp() throws Exception {
        // create Heroes for testing purposes
        hero1 = createTestHero("Dark Wizard");
        hero2 = createTestHero("Dragon Boy");
        // create Roles for testing purposes
        role1 = createTestRole("Healer");
        role2 = createTestRole("Mage");
        unusedRole = createTestRole("Unused Role");

        troop = createTestTroop("SuperTroop");
        troop2 = createTestTroop("Hero 2 Troop");
        troopDao.save(troop);
        troopDao.save(troop2);

        troop2.addHero(hero2);

        // persist roles
        roleDao.save(role1);
        assertTrue(roleDao.findAll().contains(role1));
        roleDao.save(role2);
        assertTrue(roleDao.findAll().contains(role2));
        roleDao.save(unusedRole);
        assertTrue(roleDao.findAll().contains(unusedRole));


        // assing roles to Heroes
        hero1.addRole(role1);
        hero2.addRole(role2);

        // persist them
        heroDao.save(hero1);
        assertTrue(heroDao.findAll().contains(hero1));
        heroDao.save(hero2);
        assertTrue(heroDao.findAll().contains(hero2));
    }

    @Test
    public void testGetHeroById() throws Exception {
        // try to find hero1 by it's ID
        Hero hero = heroDao.findOne(hero1.getId());
        assertNotNull(hero, "Failed to find anything");
        assertEquals(hero, hero1, "Returned wrong Hero");
    }

    @Test
    public void testGetHeroByNonExistingId() throws Exception {
        // try to find some hero by a nonsensical ID
        Hero hero = heroDao.findOne(-10L);
        assertNull(hero, "Result should have been null, but still found something");
    }

    @Test
    public void testFindByNameHero1() throws Exception {
        Hero hero = heroDao.findByName(hero1.getName());
        assertEquals(hero1, hero, "Found wrong hero by name");
    }

    @Test
    public void testFindByNameNonExistingHero() throws Exception {
        Hero hero = heroDao.findByName("XXXXXXXXXXXXXXXXXXXXXXX");
        assertNull(hero, "Found a hero with a non existing name");
    }

    @Test
    public void testCreatingNewHero() throws Exception {
        // create a new testing hero
        Hero newHero = createTestHero("TEST");
        // persist it
        heroDao.save(newHero);

        // test proper insertion
        assertEquals(heroDao.findAll().size(), 3);
        assertTrue(heroDao.findAll().contains(newHero),
                "Failed to add new Hero");
        assertTrue(heroDao.findAll().contains(hero1),
                "Somehow managed to delete an already existing Hero");
        assertTrue(heroDao.findAll().contains(hero2),
                "Somehow managed to delete an already existing Hero");

    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void testCreatingAlreadyExistingHero() throws Exception {
        // create an already existing hero (same name)
        Hero alreadyExistingHero = createTestHero(hero1.getName());
        // persist it
        heroDao.save(alreadyExistingHero);
    }

    @Test
    public void testDeleteExistingHero() throws Exception {
        // delete hero2
        heroDao.delete(hero2);

        assertEquals(heroDao.findAll().size(), 1, "Failed to delete anything");
        assertTrue(heroDao.findAll().contains(hero1), "Deleted wrong hero");
        assertFalse(heroDao.findAll().contains(hero2), "Failed to delete the right hero");
    }

    @Test
    public void testListAllHeroes() throws Exception {
        assertEquals(heroDao.findAll().size(), 2, "Found wrong number of results");
        assertTrue(heroDao.findAll().contains(hero1), "One of the heroes is missing - " + hero1.getName());
        assertTrue(heroDao.findAll().contains(hero2), "One of the heroes is missing - " + hero2.getName());
    }

    @Test
    public void testAddRoleToHero1() throws Exception {
        Hero hero = heroDao.findOne(hero1.getId());
        hero.addRole(unusedRole);
        Hero result = heroDao.findOne(hero.getId());
        assertTrue(result.getRoles().contains(unusedRole));
        assertTrue(result.getRoles().contains(role1));
        assertEquals(result.getRoles().size(), 2);
    }

    @Test
    public void testSetToopForHero1() throws Exception {
        Hero hero = heroDao.findOne(hero1.getId());
        hero.setTroop(troop);
        heroDao.save(hero);
        Hero resultHero = heroDao.findOne(hero.getId());
        Troop resultTroop = troopDao.findOne(troop.getId());

        assertEquals(resultTroop.getHeroes().size(), 1);
        assertTrue(resultTroop.getHeroes().contains(hero1));
        assertEquals(resultHero.getTroop(), (resultTroop));
    }

    @Test
    public void testUnSetTroopFromHero2() throws Exception {
        Hero hero = heroDao.findOne(hero2.getId());
        assertTrue(troop2.getHeroes().contains(hero));
        hero.setTroop(null);
        heroDao.save(hero);
        troopDao.save(troop2);

        Hero resultHero = heroDao.findOne(hero.getId());
        Troop resultTroop = troopDao.findOne(troop2.getId());

        assertEquals(resultTroop.getHeroes().size(), 0);
        assertNull(resultHero.getTroop());

    }

    @Test
    public void testRemoveRoleFromHero() throws Exception {
        Hero hero = heroDao.findOne(hero1.getId());
        hero.removeRole(role1);

        Hero result = heroDao.findOne(hero.getId());
        assertFalse(result.getRoles().contains(role1));
        assertEquals(result.getRoles().size(), 0);
    }

    @Test
    public void testAddSameRoleToMultipleHeroes() throws Exception {
        // get hero1
        Hero hero = heroDao.findOne(hero1.getId());
        // assign role2 to him
        hero.addRole(role2);
        hero.removeRole(role1);
        hero.addRole(unusedRole);

        Hero result = heroDao.findOne(hero.getId());
        assertFalse(result.getRoles().contains(role1));
        assertTrue(result.getRoles().contains(role2));
        assertTrue(result.getRoles().contains(unusedRole));
        assertEquals(result.getRoles().size(), 2);
    }

    private Troop createTestTroop(String name) {
        Troop troop = new Troop();
        troop.setName(name);
        troop.setGold(12345);
        troop.setMission("Mission for " + name);
        return troop;
    }

    /**
     * Creates and returns an instance of the Hero object for testing
     */
    private Hero createTestHero(String name) {
        // create a hero
        Hero hero = new Hero();
        hero.setName(name);
        hero.setLevel(1);

        // and return it
        return hero;
    }

    /**
     * Creates and returns an instance of the Role object for testing
     */
    private Role createTestRole(String name) {
        // create a role
        Role role = new Role();
        role.setName(name);
        role.setDescription("Role for testing purposes");

        // and return it
        return role;
    }
}