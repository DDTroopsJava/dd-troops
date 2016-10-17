package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.PersistenceSampleApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

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
    protected HeroDao heroDao;

    @PersistenceContext
    private EntityManager em;

    @org.junit.Test
    public void findById() throws Exception {

    }

    @org.junit.Test
    public void create() throws Exception {

    }

    @org.junit.Test
    public void delete() throws Exception {

    }

    @org.junit.Test
    public void findAll() throws Exception {

    }

    @org.junit.Test
    public void findByName() throws Exception {

    }

}