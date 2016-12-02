package cz.fi.muni.pa165.ddtroops.service.services;

import cz.fi.muni.pa165.ddtroops.dao.RoleDao;
import cz.fi.muni.pa165.ddtroops.entity.Role;
import cz.fi.muni.pa165.ddtroops.service.config.ServiceConfiguration;
import cz.fi.muni.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
import org.hibernate.service.spi.ServiceException;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

/**
 * Created by Petr Koláček
 *
 * @author Petr Koláček
 */
@DirtiesContext
@ContextConfiguration(classes = ServiceConfiguration.class)
public class RoleServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private RoleDao roleDao;

    @Autowired
    @InjectMocks
    private RoleService roleService;

    private Role role1;
    private Role role2;
    private Role role3;
    private List<Role> roles = new ArrayList<>();

    @BeforeMethod
    public void prepareTestRoles() {
        role1 = TestUtils.createRole("Role one");
        role2 = TestUtils.createRole("Role two");
        role3 = TestUtils.createRole("Role three");

        role1.setId(1L);
        role2.setId(2L);
        role3.setId(3L);

        roles = new ArrayList<>();

        roles.add(new Role("root"));
        roles.add(role1);
        roles.add(role2);
        roles.add(role3);
    }

    @BeforeClass
    public void setupMocks() throws ServiceException {
        MockitoAnnotations.initMocks(this);
        when(roleDao.save(any(Role.class))).thenAnswer(invoke -> {

            Role mockedRole = invoke.getArgumentAt(0, Role.class);
            if (mockedRole.getId() != null) {
                roles.set(mockedRole.getId().intValue(), mockedRole);
                return mockedRole;
            }
            if (roleDao.findByName(mockedRole.getName()) != null) {
                throw new IllegalArgumentException("Role alredy exists!");
            }
            mockedRole.setId((long) roles.size());
            roles.add(mockedRole);
            return mockedRole;
        });

        when(roleDao.findOne(anyLong())).thenAnswer(invoke -> {

            int argumentAt = invoke.getArgumentAt(0, Long.class).intValue();
            if (argumentAt >= roles.size()) return null;
            return roles.get(argumentAt);
        });

        when(roleDao.findByName(anyString())).thenAnswer(invoke -> {
            String arg = invoke.getArgumentAt(0, String.class);
            Optional<Role> optRole = roles.stream().filter((role) -> role.getName().equals(arg)).findFirst();
            return optRole.orElse(null);
        });

        when(roleDao.findAll()).thenAnswer(invoke -> Collections.unmodifiableList(roles));

        doAnswer((InvocationOnMock invoke) -> {
            Role mockedRole = invoke.getArgumentAt(0, Role.class);
            if (mockedRole.getId() == null) {
                throw new IllegalArgumentException("The role doesn't exists!");
            } else if (mockedRole.getId() >= roles.size()) {
                throw new IllegalArgumentException("The role doesn't exists!");
            }
            roles.remove(mockedRole.getId().intValue());
            return null;
        }).when(roleDao).delete(any(Role.class));

    }

    @Test
    public void shouldReturnRoleById() throws Exception {
        assertEquals(roleService.findById(role2.getId()), role2);
        assertNotEquals(roleService.findById(role2.getId()), role3);
    }

    @Test
    public void shouldReturnRoleByName() throws Exception {
        assertEquals(roleService.findByName(role2.getName()), role2);
        assertNotEquals(roleService.findByName(role2.getName()), role3);
    }

    @Test
    public void shouldNotReturnRoleByNonExistingId() throws Exception {
        Assert.assertNull(roleService.findById(666L));
    }

    @Test
    public void shouldNotReturnRoleByNonExistingName() throws Exception {
        Assert.assertNull(roleService.findByName("Swordsman"));
    }


    @Test
    public void shouldDeleteRole() throws Exception {
        int initialSize = roles.size();
        roleService.delete(role1);
        assertEquals(initialSize - 1, roles.size());

        Role deletedRole1 = roleService.findByName(role1.getName());
        Role deletedRole2 = roleService.findByName(role2.getName());
        Assert.assertNull(deletedRole1);
        Assert.assertNotNull(deletedRole2);
    }

    @Test(expectedExceptions = DDTroopsServiceException.class)
    public void shouldNotDeleteNonExistingRole() throws Exception {
        roleService.delete(new Role("Archer"));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldNotDeleteNonExistingRoleByName() throws Exception {
        roleService.delete(roleService.findByName("Baker"));
    }

    @Test(expectedExceptions = DDTroopsServiceException.class)
    public void shouldNotAddDuplicateTroop() throws Exception {
        roleService.create(new Role("Role two"));
    }

    @Test(expectedExceptions = DDTroopsServiceException.class)
    public void shouldNotAddDuplicateTroopByEdit() throws Exception {
        roleService.update(new Role("Role three"));
    }

}
