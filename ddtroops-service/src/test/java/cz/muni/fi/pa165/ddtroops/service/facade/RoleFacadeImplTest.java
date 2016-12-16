package cz.muni.fi.pa165.ddtroops.service.facade;

import static org.testng.Assert.*;

import cz.muni.fi.pa165.ddtroops.dto.RoleDTO;
import cz.muni.fi.pa165.ddtroops.facade.RoleFacade;
import cz.muni.fi.pa165.ddtroops.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * /**
 * Created by Petr Koláček
 *
 * @author Petr Koláček
 */
@DirtiesContext
@ContextConfiguration(classes = ServiceConfiguration.class)
public class RoleFacadeImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private RoleFacade roleFacade;

    private RoleDTO role1;
    private RoleDTO role2;
    private RoleDTO role3;


    @BeforeMethod
    public void createTestRoles() {
        role1 = new RoleDTO("Archer", "Long bow man");
        role2 = new RoleDTO("Pikeman", "The most universal troop role");
        role3 = new RoleDTO("Swordsman", "The heaviest one");

        roleFacade.create(role1);
        assertEquals(roleFacade.findById(role1.getId()), role1);

        roleFacade.create(role2);
        assertEquals(roleFacade.findById(role2.getId()), role2);

        roleFacade.create(role3);
        assertEquals(roleFacade.findById(role3.getId()), role3);

    }

    @AfterMethod
    public void deleteRoles() {
        if (roleFacade.findAll().contains(role1)) {
            roleFacade.delete(role1.getId());
            assertFalse(TestUtils.toSet(roleFacade.findAll()).contains(role1));
        }

        if (roleFacade.findAll().contains(role2)) {
            roleFacade.delete(role2.getId());
            assertFalse(TestUtils.toSet(roleFacade.findAll()).contains(role2));
        }

        if (roleFacade.findAll().contains(role3)) {
            roleFacade.delete(role3.getId());
            assertFalse(TestUtils.toSet(roleFacade.findAll()).contains(role3));
        }
    }


    @Test
    public void testFindById() throws Exception {
        assertEquals(roleFacade.findById(role1.getId()), role1);
        assertEquals(roleFacade.findById(role2.getId()), role2);
        assertEquals(roleFacade.findById(role3.getId()), role3);
        assertNull(roleFacade.findById(666L));
    }

    @Test
    public void testFindByName() throws Exception {
        assertEquals(roleFacade.findByName(role1.getName()), role1);
        assertEquals(roleFacade.findByName(role2.getName()), role2);
        assertEquals(roleFacade.findByName(role3.getName()), role3);
        assertNull(roleFacade.findByName("Horseman"));
    }

    @Test
    public void testUpdate() throws Exception {
        role2.setDescription("Everybody wants to be a pikeman");
        roleFacade.update((role2));
        assertEquals(roleFacade.findAll().size(), 3);
        assertEquals(roleFacade.findById(role2.getId()), role2);
        assertEquals(roleFacade.findById(role2.getId()).getDescription(), "Everybody wants to be a pikeman");
    }

    @Test
    public void testFindAll() throws Exception {
        assertTrue(roleFacade.findAll().contains(role1));
        assertTrue(roleFacade.findAll().contains(role2));
        assertTrue(roleFacade.findAll().contains(role3));
    }

    @Test
    public void testDelete() throws Exception {
        assertNotNull(roleFacade.findById(role1.getId()));
        roleFacade.delete(role1.getId());
        assertNull(roleFacade.findById(role1.getId()));
        assertFalse(roleFacade.findAll().contains(role1));
    }
    



}
