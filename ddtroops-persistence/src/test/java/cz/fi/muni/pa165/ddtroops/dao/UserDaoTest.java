
package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.ddtroops.entity.User;

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

    @BeforeMethod
    public void createUsers() {
        user1 = getUser("Rambo");
        user2 = getUser("Rocky");

        userDao.create(user1);
        Assert.assertTrue(userDao.listAll().contains(user1));

        userDao.create(user2);
        Assert.assertTrue(userDao.listAll().contains(user2));

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
        userDao.create(user);

        Assert.assertNotEquals(user.getId(), 0);

        User fromDb = userDao.findById(user.getId());
        Assert.assertEquals(fromDb, user);

    }


    @Test(expectedExceptions = JpaSystemException.class)
    public void createAlreadyCreatedUser() {
        User u_new = getUser(user1.getName());
        userDao.create(u_new);
    }


    @Test
    public void listUsers() {
        Assert.assertTrue(userDao.listAll().contains(user1));
        Assert.assertTrue(userDao.listAll().contains(user2));
        Assert.assertEquals(userDao.listAll().size(), 2);

    }

    @Test
    public void deleteUser() {
        userDao.delete(user1);

        Assert.assertEquals(userDao.listAll().size(), 1);
        Assert.assertTrue(userDao.listAll().contains(user2));
        Assert.assertFalse(userDao.listAll().contains(user1));
    }


    @Test
    public void deleteNonExistingUser() {
        User u = getUser("Hancock");
        userDao.delete(u);

        Assert.assertEquals(userDao.listAll().size(), 2);
        Assert.assertTrue(userDao.listAll().contains(user2));
        Assert.assertTrue(userDao.listAll().contains(user1));
    }


    @Test
    public void updateUser() {
        User up = userDao.findById(user1.getId());
        up.setAdmin(true);
        up.setPhone("123456789");
        up.setName("Updated name");

        userDao.update(up);

        User my_user = userDao.findById(user1.getId());

        Assert.assertEquals(my_user.getPhone(), "123456789");
        Assert.assertEquals(my_user.getName(), "Updated name");
        Assert.assertTrue(my_user.isAdmin());
    }

    private static User getUser(String name) {
        User user = new User();
        user.setEmail(name + "@example.com");
        user.setName(name);
        user.setJoinedDate(new Date());
        return user;
    }
}