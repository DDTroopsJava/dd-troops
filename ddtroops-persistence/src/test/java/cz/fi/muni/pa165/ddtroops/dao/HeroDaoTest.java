package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.ddtroops.entity.Hero;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * Created by pstanko.
 *
 * @author pstanko
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class HeroDaoTest extends AbstractTestNGSpringContextTests {


    @Autowired
    private HeroDao heroDao;

    @PersistenceContext
    private EntityManager em;


    private Hero hero1;
    private Hero hero2;

    @BeforeMethod
    public void createHeroes() {
        hero1 = getHero("root");
        hero2 = getHero("moot");

        heroDao.create(hero1);
        heroDao.create(hero2);
    }

    @org.junit.Test
    public void shouldFindHero1ByItsId() throws Exception {
        Hero u = heroDao.findById(hero1.getId());

        Assert.assertNotNull(u);
        Assert.assertEquals(u, hero1);
    }

    @org.junit.Test
    public void shouldNotFindAnyUserByNonExistingId() throws Exception {
        Hero u = heroDao.findById(-10L);

        Assert.assertNull(u);
    }

    @org.junit.Test
    public void shouldCreateNewHero() throws Exception {
        Hero hero3 = getHero("lambda");

        heroDao.create(hero3);

        Assert.assertEquals(heroDao.findAll().size(), 3);
        Assert.assertTrue(heroDao.findAll().contains(hero3));
        Assert.assertTrue(heroDao.findAll().contains(hero2));
        Assert.assertTrue(heroDao.findAll().contains(hero1));
    }


    @Test(expectedExceptions = JpaSystemException.class)
    public void shouldNotCreateAlreadyExistingUser() throws Exception {
        Hero hero3 = getHero(hero1.getName());
        heroDao.create(hero3);
    }

    @Test
    public void shouldDeleteExistingUsers() throws Exception {
        heroDao.delete(hero1);

        Assert.assertEquals(heroDao.findAll().size(), 1);
        Assert.assertTrue(heroDao.findAll().contains(hero2));
        Assert.assertFalse(heroDao.findAll().contains(hero1));
    }

    @org.junit.Test
    public void shouldFindAllHeroes() throws Exception {

        Assert.assertEquals(heroDao.findAll().size(), 2);
        Assert.assertTrue(heroDao.findAll().contains(hero2));
        Assert.assertTrue(heroDao.findAll().contains(hero1));

    }

    @org.junit.Test
    public void shouldFindUser1ByItsName() throws Exception {
        Hero u = heroDao.findByName(hero1.getName());
        Assert.assertNotNull(u);
        Assert.assertEquals(u, hero1);
    }

    @org.junit.Test
    public void shouldNotFindAnyUserByNonExistingName() throws Exception {
        Hero u = heroDao.findByName("asdadsadasdasdas");
        Assert.assertNull(u);
    }

    private Hero getHero(String name) {
        Hero hero = new Hero();
        hero.setName(name);
        hero.setExperienceLevel(1);
        hero.setGroup(null);
        return hero;
    }

}