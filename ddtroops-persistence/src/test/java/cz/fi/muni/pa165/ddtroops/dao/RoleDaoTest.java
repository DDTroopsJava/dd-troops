package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.ddtroops.entity.Hero;
import cz.fi.muni.pa165.ddtroops.entity.Role;
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
 * @author Petr Kolacek
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class RoleDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private HeroDao heroDao;


    private Role role1;
    private Role role2;
    private Role role3;

    private Hero hero;

    /**
     * Create instance of Role for testing
     */
    private static Role createRole(String name, String description) {
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);
        return role;
    }

    /**
     * Create instance of Hero for testing
     */
    private static Hero createHero(String name) {
        Hero hero = new Hero();
        hero.setName(name);
        hero.setLevel(1);
        return hero;
    }

    @BeforeMethod
    public void setUp() throws Exception {
        // create role instances
        role1 = createRole("Archer", "Long bow archer");
        role2 = createRole("Swordsman", "Invincible fighter");
        role3 = createRole("Crossbowman", "Fast and furious");

        hero = createHero("Hero");

        // persist
        roleDao.save(role1);
        assertTrue(roleDao.findAll().contains(role1));

        roleDao.save(role2);
        assertTrue(roleDao.findAll().contains(role2));
        roleDao.save(role3);
        assertTrue(roleDao.findAll().contains(role3));


        heroDao.save(hero);
        assertTrue(heroDao.findAll().contains(hero));

    }

    @Test
    public void testGetRoleById() throws Exception {
        Role role = roleDao.findOne(role1.getId());
        assertNotNull(role, "None role exists.");
        assertEquals(role, role1, "Wrong role returned.");
    }

    @Test
    public void testGetRoleByNonExistingId() throws Exception {
        Role role = roleDao.findOne(-10L);
        assertNull(role, "Should be null.");
    }

    @Test
    public void testGetRolesByNames() throws Exception {
        Role role_1 = roleDao.findByName(role1.getName());
        Role role_2 = roleDao.findByName(role2.getName());
        Role role_3 = roleDao.findByName(role3.getName());
        assertEquals(role1, role_1, "Found wrong role by name.");
        assertEquals(role2, role_2, "Found wrong role by name.");
        assertEquals(role3, role_3, "Found wrong role by name.");
    }

    @Test
    public void testGetRoleByNonExistingName() throws Exception {
        Role role = roleDao.findByName("Pikeman");
        assertNull(role, "Found a role with a non existing name.");
    }

    @Test
    public void testCreateNewRole() throws Exception {
        Role role = createRole("Pikeman", "Very universal soldier.");
        roleDao.save(role);

        assertEquals(roleDao.findAll().size(), 4);
        assertTrue(roleDao.findAll().contains(role),
                "Failed to add new Role");
        assertTrue(roleDao.findAll().contains(role1),
                "Somehow managed to delete an already existing Role");

    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void testCreatingAlreadyExistingRole() throws Exception {
        Role role = createRole(role2.getName(), role2.getDescription());
        roleDao.save(role);
    }

    @Test
    public void testHeroAssignedRole() throws Exception {
        role1.addHero(hero);
        role2.addHero(hero);

        Hero resultHero = heroDao.findOne(hero.getId());

        assertTrue(resultHero.getRoles().contains(role1), "Failed to assign role 1.");
        assertTrue(role1.getHeroes().contains(resultHero));

        assertTrue(resultHero.getRoles().contains(role2), "Failed to assign role 2.");
        assertTrue(role2.getHeroes().contains(resultHero));
        assertFalse(resultHero.getRoles().contains(role3), "Role 3 must be unassigned.");
    }

    @Test
    public void testDeleteExistingRole() throws Exception {
        roleDao.delete(role3);
        assertEquals(roleDao.findAll().size(), 2, "Failed to delete role.");
        assertFalse(roleDao.findAll().contains(role3), "Failed to delete the right role.");
    }

    @Test
    public void testDeleteExistingRoleWithHero() throws Exception {
        role1.addHero(hero);
        roleDao.delete(role1);
        assertEquals(roleDao.findAll().size(), 2, "Failed to delete role.");
        assertFalse(roleDao.findAll().contains(role1), "Failed to delete the right role.");
    }

}