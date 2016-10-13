package cz.fi.muni.pa165.ddtroops.dao;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by pstanko.
 *
 * @author pstanko
 */
public class HeroDaoImplTest {

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