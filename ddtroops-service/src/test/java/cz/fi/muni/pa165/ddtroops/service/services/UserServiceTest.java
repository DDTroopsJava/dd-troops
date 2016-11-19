package cz.fi.muni.pa165.ddtroops.service.services;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import cz.fi.muni.pa165.ddtroops.dao.UserDao;
import cz.fi.muni.pa165.ddtroops.entity.User;
import cz.fi.muni.pa165.ddtroops.exceptions.DDTroopsServiceException;
import cz.fi.muni.pa165.ddtroops.service.config.ServiceConfiguration;
import org.dozer.Mapper;
import org.hibernate.service.spi.ServiceException;
import org.junit.Assert;
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

    public static final String PASSWORD_123 = "password123";
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

        for(long i = 3L; i <= 5L;i++){
            users.add(TestUtils.createUser("user" + 1));
        }
    }


    @BeforeClass
    public void setupMocks() throws ServiceException
    {
        MockitoAnnotations.initMocks(this);
        when(userDao.save(any(User.class))).thenAnswer(invoke -> {
            User mockedUser = invoke.getArgumentAt(0, User.class);
            if(mockedUser.getId() != null){
                users.set(mockedUser.getId().intValue(), mockedUser);
                return mockedUser;
            }
            if(userDao.findByEmail(mockedUser.getEmail()) != null){
                throw new IllegalArgumentException("User alredy exists!");
            }
            mockedUser.setId( (long) users.size() );
            users.add(mockedUser);
            return mockedUser;
        });

        when(userDao.findOne(anyLong())).thenAnswer(invoke  -> {

            int argumentAt = invoke.getArgumentAt(0, Long.class).intValue();
            if (argumentAt >= users.size()) return null;
            return users.get(argumentAt);
        });

        when(userDao.findByEmail(anyString())).thenAnswer( invoke -> {
            String arg = invoke.getArgumentAt(0, String.class);
            Optional<User> optUser = users.stream().filter((user) -> user.getEmail().equals(arg)).findFirst();
            if(!optUser.isPresent()){
                return null;
            }
            return optUser.get();
        });

        when(userDao.findAll()).thenAnswer( invole -> Collections.unmodifiableList(users));

    }

    @Test
    public void shouldRegisterNewUser() throws Exception {
        int origSize = users.size();
        User newUser = TestUtils.createUser("new_user");
        userService.register(newUser, PASSWORD_123);
        assertEquals(users.size(), origSize + 1);
        assertEquals(newUser.getId().intValue(),  users.size() - 1); // should have next id
        assertTrue(users.contains(newUser));

    }

    @Test(expectedExceptions = {DDTroopsServiceException.class})
    public void shouldNotRegisterExistingUser() throws Exception {
        int origSize = users.size();
        User newUser = TestUtils.createUser("new_user");
        User newUser2 = TestUtils.createUser("new_user");
        userService.register(newUser, PASSWORD_123);
        userService.register(newUser2, PASSWORD_123);
    }

    @Test
    public void shouldUpdateExistingUser() throws Exception
    {
        int origSize = users.size();
        long origId = testUser.getId();
        testUser.setName("New name");
        testUser.setPhone("0123456789");
        userService.update(testUser);
        assertEquals((long)testUser.getId(), origId);
        assertEquals(origSize, users.size());
        User user = userService.findById(testUser.getId());
        assertEquals(user, testUser);
        assertEquals(user.getName(), "New name");
        assertEquals(user.getPhone(), "0123456789");
    }

    @Test
    public void shouldUpdateNewUserPassword() throws Exception
    {
        User newUser = TestUtils.createUser("new_user");
        userService.register(newUser, PASSWORD_123);
        String passHash = newUser.getPasswordHash();
        userService.updatePassword(newUser, PASSWORD_123, "NewPassword123");
        User user = userService.findById(newUser.getId());
        assertNotEquals(passHash,user.getPasswordHash());
    }

    @Test
    public void shouldFindAllUsers() throws Exception
    {
        assertEquals(userService.findAll().size(), 6);

    }

    @Test
    public void shouldAuthentificateUserWithValidCredentials() throws Exception
    {
        User newUser = TestUtils.createUser("new_user");
        userService.register(newUser, PASSWORD_123);

        assertTrue(userService.authenticate(newUser, PASSWORD_123));
    }

    @Test
    public void shouldNotAuthentificateUserWithInvalidCredential() throws Exception
    {
        User newUser = TestUtils.createUser("new_user");
        userService.register(newUser, PASSWORD_123);

        assertFalse(userService.authenticate(newUser, "NotValid123"));
    }

    @Test
    public void shouldBeAdminWhenIsAdmin() throws Exception {
        assertTrue(testAdmin.isAdmin());
        assertFalse(testUser.isAdmin());
    }

    @Test
    public void shouldReturnValidUserById() throws Exception {
        assertEquals(userService.findById(testUser.getId()), testUser);
        assertNotEquals(userService.findById(testUser.getId()), testAdmin);
    }

    public void shouldReturnNullWhenNonExistingId() throws Exception {
        Assert.assertNull(userService.findById(1000L));
    }

    @Test
    public void shouldReturnValidUserByEmail() throws Exception {
        User userByEmail = userService.findByEmail(testUser.getEmail());
        assertEquals(userByEmail, testUser);
        assertNotEquals(userService.findByEmail(testUser.getEmail()), testAdmin);
    }

    public void shouldReturnNullWhenNonExistingEmail() throws Exception {
        Assert.assertNull(userService.findByEmail("nonexist@example.com"));
    }

}