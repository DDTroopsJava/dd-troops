package cz.muni.fi.pa165.ddtroops.dao;

import cz.muni.fi.pa165.ddtroops.PersistenceSampleApplicationContext;
import cz.muni.fi.pa165.ddtroops.entity.User;
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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Peter Zaoral.
 *
 * @author P. Zaoral
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserDao userDao;

    private User user1;
    private User user2;

    private static Set<User> toSet(Iterable<User> iterUsers) {
        Set<User> result = new HashSet<>();
        for (User item : iterUsers) {
            result.add(item);
        }
        return result;
    }

    private static User getUser(String name) {
        User user = new User();
        user.setEmail(name + "@example.com");
        user.setName(name);
        user.setJoinedDate(new Date());
        return user;
    }

    @BeforeMethod
    public void createUsers() {
        user1 = getUser("Rambo");
        user2 = getUser("Rocky");
        userDao.save(user1);
        Assert.assertTrue(toSet(userDao.findAll()).contains(user1));


        userDao.save(user2);
        Assert.assertTrue(toSet(userDao.findAll()).contains(user2));

    }

    @Test
    public void getByEmail() {
        Assert.assertNotNull(userDao.findByEmail(user1.getEmail()));
    }

    @Test
    public void getByNonExistingEmail() {
        Assert.assertNull(userDao.findByEmail("BADMAIL"));
    }

    @Test
    public void createUserNewUser() {
        User user = new User();
        user.setEmail("root@localhost.com");
        user.setName("root");
        user.setAdmin(true);
        user.setJoinedDate(new Date());
        userDao.save(user);

        Assert.assertNotEquals(user.getId(), 0);

        User fromDb = userDao.findOne(user.getId());
        Assert.assertEquals(fromDb, user);

    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void createAlreadyCreatedUser() {
        User u_new = getUser(user1.getName());
        userDao.save(u_new);
    }

    @Test
    public void listUsers() {
        Assert.assertTrue(toSet(userDao.findAll()).contains(user1));
        Assert.assertTrue(toSet(userDao.findAll()).contains(user2));
        Assert.assertEquals(toSet(userDao.findAll()).size(), 2);

    }

    @Test
    public void deleteUser() {
        userDao.delete(user1);

        Assert.assertEquals(toSet(userDao.findAll()).size(), 1);
        Assert.assertTrue(toSet(userDao.findAll()).contains(user2));
        Assert.assertFalse(toSet(userDao.findAll()).contains(user1));
    }

    @Test
    public void deleteNonExistingUser() {
        User u = getUser("Hancock");
        userDao.delete(u);

        Assert.assertEquals(toSet(userDao.findAll()).size(), 2);
        Assert.assertTrue(toSet(userDao.findAll()).contains(user2));
        Assert.assertTrue(toSet(userDao.findAll()).contains(user1));
    }

    @Test
    public void updateUser() {
        User up = userDao.findOne(user1.getId());
        up.setAdmin(true);
        up.setPhone("123456789");
        up.setName("Updated name");

        userDao.save(up);

        User my_user = userDao.findOne(user1.getId());

        Assert.assertEquals(my_user.getPhone(), "123456789");
        Assert.assertEquals(my_user.getName(), "Updated name");
        Assert.assertTrue(my_user.isAdmin());
    }
}