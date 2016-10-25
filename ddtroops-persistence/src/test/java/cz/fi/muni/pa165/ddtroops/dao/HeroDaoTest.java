package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.ddtroops.entity.Hero;
import cz.fi.muni.pa165.ddtroops.entity.Role;

import javax.persistence.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.orm.jpa.*;
import org.springframework.test.context.*;
import org.springframework.test.context.testng.*;
import org.springframework.test.context.transaction.*;
import org.springframework.transaction.annotation.*;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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

    @PersistenceContext
    private EntityManager em;

    private Hero hero1;
    private Hero hero2;
    private Role role1;
    private Role role2;
    private Role unusedRole;

    @BeforeMethod
    public void setUp() throws Exception {
        // create Heroes for testing purposes
        hero1 = createTestHero("Dark Wizard");
        hero2 = createTestHero("Dragon Boy");
        // create Roles for testing purposes
        role1 = createTestRole("Healer");
        role2 = createTestRole("Mage");
        unusedRole = createTestRole("Unused Role");
        
        // persist roles
        roleDao.create(role1);
        roleDao.create(role2);
        roleDao.create(unusedRole);
        
        // assing roles to Heroes
        hero1.addRole(role1);
        hero2.addRole(role2);
        
        // persist them
        heroDao.create(hero1);
        heroDao.create(hero2);
    }

    @Test
    public void testGetHeroById() throws Exception {
        // try to find hero1 by it's ID
        Hero hero = heroDao.findById(hero1.getId());
        assertNotNull(hero, "Failed to find anything");
        assertEquals(hero, hero1, "Returned wrong Hero");
    }

    @Test
    public void testGetHeroByNonExistingId() throws Exception {
        // try to find some hero by a nonsensical ID
        Hero hero = heroDao.findById(-10L);
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
        heroDao.create(newHero);

        // test proper insertion
        assertEquals(heroDao.listAll().size(), 3);
        assertTrue(heroDao.listAll().contains(newHero),
                "Failed to add new Hero");
        assertTrue(heroDao.listAll().contains(hero1),
                "Somehow managed to delete an already existing Hero");
        assertTrue(heroDao.listAll().contains(hero2),
                "Somehow managed to delete an already existing Hero");

    }

    @org.testng.annotations.Test(expectedExceptions = JpaSystemException.class)
    public void testCreatingAlreadyExistingHero() throws Exception {
        // create an already existing hero (same name)
        Hero alreadyExistingHero = createTestHero(hero1.getName());
        // persist it
        heroDao.create(alreadyExistingHero);
    }

    @Test
    public void testDeleteExistingHero() throws Exception {
        // delete hero2
        heroDao.delete(hero2);

        assertEquals(heroDao.listAll().size(), 1, "Failed to delete anything");
        assertTrue(heroDao.listAll().contains(hero1), "Deleted wrong hero");
        assertFalse(heroDao.listAll().contains(hero2), "Failed to delete the right hero");
    }

    @Test
    public void testListAllHeroes() throws Exception {
        assertEquals(heroDao.listAll().size(), 2, "Found wrong number of results");
        assertTrue(heroDao.listAll().contains(hero1), "One of the heroes is missing - " + hero1.getName());
        assertTrue(heroDao.listAll().contains(hero2), "One of the heroes is missing - " + hero2.getName());
    }
    
    @Test
    public void testAddRoleToHero1() throws Exception
    {
        Hero hero = heroDao.findById(hero1.getId());
        hero.addRole(unusedRole);
        heroDao.update(hero);
        Hero result = heroDao.findById(hero.getId());
        assertTrue(result.getRoles().contains(unusedRole));
        assertTrue(result.getRoles().contains(role1));
        assertEquals(result.getRoles().size(), 2);
    }
    
    @Test
    public void testAddSameRoleToMultipleHeroes() throws Exception
    {
        // get hero1
        Hero hero = heroDao.findById(hero1.getId());
        // assign role2 to him
        hero.addRole(role2);
        
        heroDao.update(hero);
        
        Hero result = heroDao.findById(hero.getId());
        assertTrue(result.getRoles().contains(role1));
        assertTrue(result.getRoles().contains(role2));
        assertEquals(result.getRoles().size(), 2);
    }

    /**
     * Creates and returns an instance of the Hero object for testing
     */
    private Hero createTestHero(String name) {
        // create a hero
        Hero hero = new Hero();
        hero.setName(name);
        hero.setExperience(1);

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