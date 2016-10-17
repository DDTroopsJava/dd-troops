package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.ddtroops.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
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
        u1 = getSimpleUser();
        u2 = getSimpleUser2();


        userDao.create(u1);
        userDao.create(u2);
    }

    @Test
    public void findByEmail() {
        Assert.assertNotNull(userDao.findByEmail(u1.getEmail()));
    }

    @Test
    public void findByNonExistentEmail() {
        Assert.assertNull(userDao.findByEmail("asdfasdfasd"));
    }

    /**
     * Just helper method to create a valid user
     *
     * @return
     */
    private static User getSimpleUser() {
        User user = new User();
        user.setEmail("filip@seznam.cz");
        user.setGivenName("Filip");
        user.setSurname("Markovic");
        user.setAddress("Jihlava");
        user.setJoinedDate(new Date());
        return user;
    }

    private static User getSimpleUser2() {
        User user = new User();
        user.setEmail("jirka@seznam.cz");
        user.setGivenName("Jiri");
        user.setSurname("Mrkev");
        user.setAddress("Hodonin");
        user.setJoinedDate(new Date());
        return user;
    }

}