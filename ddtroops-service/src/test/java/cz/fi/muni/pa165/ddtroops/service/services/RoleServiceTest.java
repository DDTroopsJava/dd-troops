package cz.fi.muni.pa165.ddtroops.service.services;

import cz.fi.muni.pa165.ddtroops.dao.RoleDao;
import cz.fi.muni.pa165.ddtroops.entity.Role;
import cz.fi.muni.pa165.ddtroops.service.config.ServiceConfiguration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import cz.fi.muni.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
import org.dozer.Mapper;
import org.hibernate.service.spi.ServiceException;
import org.junit.Assert;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Petr Koláček
 * 
 * @author Petr Koláček
 */
@DirtiesContext
@ContextConfiguration(classes = ServiceConfiguration.class)
public class RoleServiceTest extends AbstractTestNGSpringContextTests
{
    @Mock
    private RoleDao roleDao;
    
    @Autowired
    @InjectMocks
    private RoleService roleService;
    
    @Autowired
    @Spy
    private Mapper mapper;
    
    private Role role1;
    private Role role2;
    private Role role3;
    private List<Role> roles = new ArrayList<>();
    
    @BeforeMethod
    public void prepareTestRoles()
    {
        role1 = TestUtils.createRole("Role 1");
        role2 = TestUtils.createRole("Role 2");
        role3 = TestUtils.createRole("Role 3");
        
        role1.setId(1L);
        role2.setId(2L);
        role3.setId(3L);
        
        roles = new ArrayList<>();
        roles.add(new Role("root", "root description"));
        roles.add(role1);
        roles.add(role2);
        roles.add(role3);
    }
    
    @BeforeClass
    public void setupMocks() throws ServiceException {
        MockitoAnnotations.initMocks(this);
        when(roleDao.save(any(Role.class))).thenAnswer(invoke -> {
            
            Role mockedRole = invoke.getArgumentAt(0, Role.class);
            if (mockedRole.getId() != null){
                roles.set(mockedRole.getId().intValue(), mockedRole);
                return mockedRole;
            }
            if (roleDao.findByName(mockedRole.getName()) != null){
                throw new IllegalArgumentException("Role alredy exists!");
            }
            mockedRole.setId((long)roles.size());
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
            if (!optRole.isPresent()){
                return null;
            }
            return optRole.get();
        });

        when(roleDao.findAll()).thenAnswer(invoke -> Collections.unmodifiableList(roles));
        
        doAnswer((Answer<Void>) (InvocationOnMock invoke) -> {
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
    
}
