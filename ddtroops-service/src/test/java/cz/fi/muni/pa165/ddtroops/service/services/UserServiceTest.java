package cz.fi.muni.pa165.ddtroops.service.services;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.fi.muni.pa165.ddtroops.dao.UserDao;
import cz.fi.muni.pa165.ddtroops.entity.User;
import cz.fi.muni.pa165.ddtroops.service.config.ServiceConfiguration;
import org.dozer.Mapper;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by pstanko.
 * @author pstanko
 */
@DirtiesContext
@ContextConfiguration(classes = ServiceConfiguration.class)
public class UserServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private UserDao userDao;

    @Autowired
    @InjectMocks
    private UserService userService;


    @Autowired
    @Spy
    private Mapper mapper;


    private User testUser;
    private User testAdmin;
    public static final User NEW_USER = TestUtils.createUser("new_user");


    private List<User> users = new ArrayList<>();

    @BeforeMethod
    public void prepareTestUsers(){
        testUser = TestUtils.createUser("user");
        testAdmin = TestUtils.createUser("admin");
        users = new ArrayList<>();

        testAdmin.setId(1L);
        testAdmin.setAdmin(true);
        testUser.setId(2L);

        users.add(new User("root", true));
        users.add(testAdmin);
        users.add(testUser);

        for(long i = 3L; i <= 5;i++){
            users.add(TestUtils.createUser("user" + 1));
        }
    }


    @BeforeClass
    public void setupMocks() throws ServiceException
    {
        MockitoAnnotations.initMocks(this);
        when(userDao.save(NEW_USER)).thenAnswer(invoke -> {
            User mockedUser = invoke.getArgumentAt(0, User.class);
            users.add(mockedUser);
            mockedUser.setId( (long) users.size() );
            return mockedUser;
        });

        when(userDao.findOne(anyLong())).thenAnswer(invoke  -> {

            int argumentAt = invoke.getArgumentAt(0, Long.class).intValue();
            return users.get(argumentAt);
        });

        when(userDao.findByEmail(anyString())).thenAnswer( invoke -> {
            String arg = invoke.getArgumentAt(0, String.class);
            return users.stream().filter((user) -> user.getEmail().equals(arg)).findFirst();
        });


        when(userDao.findAll()).thenReturn(Collections.unmodifiableList(users));

    }

    @Test
    public void testRegister() throws Exception {
        int origSize = users.size();
        userService.register(NEW_USER, "password123");
        assertEquals(users.size(), origSize + 1);
        assertEquals(NEW_USER.getId().intValue(),  users.size());
        assertTrue(users.contains(NEW_USER));

    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testUpdatePassword() throws Exception {

    }

    @Test
    public void testFindAll() throws Exception {

    }

    @Test
    public void testAuthenticate() throws Exception {

    }

    @Test
    public void testIsAdmin() throws Exception {

    }

    @Test
    public void testFindById() throws Exception {

    }

    @Test
    public void testFindByEmail() throws Exception {

    }

}