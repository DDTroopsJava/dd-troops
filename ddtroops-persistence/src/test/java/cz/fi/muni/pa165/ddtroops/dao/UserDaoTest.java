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
 * Created by pstanko.
 *
 * @author pstanko
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private UserDao userDao;

    private User u1 ;
    private User u2;

    @BeforeMethod
    public void createUsers() {
        u1 = getUser("stefan");
        u2 = getUser("filip");


        userDao.create(u1);
        userDao.create(u2);
    }

    @Test
    public void getByEmail() {
        Assert.assertNotNull(userDao.getByEmail(u1.getEmail()));
    }

    @Test
    public void getByNonExistingEmail() {
        Assert.assertNull(userDao.getByEmail("asdfasdfasd"));
    }

    @Test
    public void createUserNewUser()
    {
        User user = new User();
        user.setEmail("root@localhost.com");
        user.setName("root");
        user.setAdmin(true);
        user.setJoinedDate(new Date());
        userDao.create(user);

        Assert.assertNotEquals(user.getId(), 0);

        User fromDb = userDao.getById(user.getId());
        Assert.assertEquals(fromDb, user);
    }

    @Test(expectedExceptions = JpaSystemException.class)
    public void createAlreadyCreatedUser()
    {
        User u_new = getUser(u1.getName());
        userDao.create(u_new);
    }

    @Test
    public void listUsers()
    {
        Assert.assertTrue(userDao.listAll().contains(u1));
        Assert.assertTrue(userDao.listAll().contains(u2));
        Assert.assertEquals(userDao.listAll().size(), 2);
    }

    @Test
    public void deleteUser()
    {
        userDao.delete(u1);

        Assert.assertEquals(userDao.listAll().size(), 1);
        Assert.assertTrue(userDao.listAll().contains(u2));
        Assert.assertFalse(userDao.listAll().contains(u1));
    }

    @Test
    public void deleteNonExistingUser()
    {
        User u = getUser("adam");
        userDao.delete(u);

        Assert.assertEquals(userDao.listAll().size(), 2);
        Assert.assertTrue(userDao.listAll().contains(u2));
        Assert.assertTrue(userDao.listAll().contains(u1));
    }


    @Test
    public void updateUser()
    {
        User up = userDao.getById(u1.getId());
        up.setAdmin(true);
        up.setPhone("123456789");
        up.setName("Updated name");

        userDao.update(up);

        User my_user = userDao.getById(u1.getId());

        Assert.assertEquals(my_user.getPhone(), "123456789");
        Assert.assertEquals(my_user.getName(), "Updated name");
        Assert.assertTrue(my_user.isAdmin());

    }

    /**
     * Just helper method to create a valid user
     *
     * @return
     */


    private static User getUser(String name)
    {
        User user = new User();
        user.setEmail(name + "@example.com");
        user.setName(name);
        user.setJoinedDate(new Date());
        return user;
    }



}