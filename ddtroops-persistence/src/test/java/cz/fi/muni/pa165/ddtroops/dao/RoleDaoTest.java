package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.ddtroops.entity.Role;
import cz.fi.muni.pa165.ddtroops.entity.Hero;

import javax.persistence.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.orm.jpa.*;
import org.springframework.test.context.*;
import org.springframework.test.context.testng.*;
import org.springframework.test.context.transaction.*;
import org.springframework.transaction.annotation.*;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;


/**
 * Created by pstanko.
 *
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
    
    @PersistenceContext
    private EntityManager em;
    
    private Role role1;
    private Role role2;
    private Role role3;
    
    private Hero hero;
    
    @BeforeMethod
    public void setUp() throws Exception {
        // create role instances
        role1 = createRole("Archer", "Long bow archer");
        role2 = createRole("Swordsman", "Invincible fighter");
        role3 = createRole("Crossbowman", "Fast and furious");
        
        hero = createHero("Hero");
        
        // persist
        roleDao.create(role1);
        roleDao.create(role2);
        roleDao.create(role3);
        
        heroDao.create(hero);
    }
    
    @Test
    public void testGetRoleById() throws Exception 
    {
        Role role = roleDao.findById(role1.getId());
        assertNotNull(role, "None role exists.");
        assertEquals(role, role1, "Wrong role returned.");
    }
    
    @Test
    public void testGetRoleByNonExistingId() throws Exception
    {
        Role role = roleDao.findById(-10L);
        assertNull(role, "Should be null.");
    }
    
    @Test 
    public void testGetRolesByNames() throws Exception
    {
        Role role_1 = roleDao.findByName(role1.getName());
        Role role_2 = roleDao.findByName(role2.getName());
        Role role_3 = roleDao.findByName(role3.getName());
        assertEquals(role1, role_1, "Found wrong role by name.");
        assertEquals(role2, role_2, "Found wrong role by name.");
        assertEquals(role3, role_3, "Found wrong role by name.");
    }
    
    @Test
    public void testGetRoleByNonExistingName() throws Exception
    {
        Role role = roleDao.findByName("Pikeman");
        assertNull(role, "Found a role with a non existing name.");
    }
    
    @Test
    public void testCreateNewRole() throws Exception
    {
        Role role = createRole("Pikeman", "Very universal soldier.");
        roleDao.create(role);
        
        assertEquals(roleDao.listAll().size(), 4);
        assertTrue(roleDao.listAll().contains(role),
                "Failed to add new Role");
        assertTrue(roleDao.listAll().contains(role1),
                "Somehow managed to delete an already existing Role");
        
    }
    
    @org.testng.annotations.Test(expectedExceptions = JpaSystemException.class)
    public void testCreatingAlreadyExistingRole() throws Exception {
        Role role = createRole(role2.getName(), role2.getDescription());
        roleDao.create(role);
    }
    
    @Test
    public void testHeroAssignedRole() throws Exception
    {
        hero.addRole(role1);
        hero.addRole(role2);
        assertTrue(hero.getRoles().contains(role1), "Failed to assign role 1.");
        assertTrue(hero.getRoles().contains(role2), "Failed to assign role 2.");
        assertFalse(hero.getRoles().contains(role3), "Role 3 must be unassigned.");
    }
    
    @Test
    public void testDeleteExistingRole() throws Exception
    {
        roleDao.delete(role3);
        assertEquals(roleDao.listAll().size(), 3, "Failed to delete role.");
        assertFalse(roleDao.listAll().contains(role3), "Failed to delete the right role.");
    }
    
    /**
     * Create instance of Role for testing
     */
    private static Role createRole(String name, String description)
    {
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);
        return role;
    }
    
    /**
     * Create instance of Hero for testing
     */
    private static Hero createHero(String name)
    {
        Hero hero = new Hero();
        hero.setName(name);
        hero.setExperience(1);
        return hero;
    }
    
    
}