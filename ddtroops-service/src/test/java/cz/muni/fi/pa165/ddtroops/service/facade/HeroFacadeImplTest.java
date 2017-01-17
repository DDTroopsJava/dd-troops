package cz.muni.fi.pa165.ddtroops.service.facade;

import static org.testng.Assert.*;

import cz.muni.fi.pa165.ddtroops.dto.HeroDTO;
import cz.muni.fi.pa165.ddtroops.facade.HeroFacade;
import cz.muni.fi.pa165.ddtroops.service.config.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Peter Zaoral.
 *
 * @author Peter Zaoral.
 */
@DirtiesContext
@ContextConfiguration(classes = ServiceConfiguration.class)
public class HeroFacadeImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    @InjectMocks
    private HeroFacade heroFacade;

    private HeroDTO hero1;
    private HeroDTO hero2;
    private HeroDTO newHero;


    @BeforeMethod
    public void createHeroes() {
        hero1 = getHeroHelper("Batman");
        hero2 = getHeroHelper("Superman");
        newHero = getHeroHelper("new-hero");

        heroFacade.create(hero1);
        assertTrue(TestUtils.toSet(heroFacade.findAll()).contains(hero1));

        heroFacade.create(hero2);
        assertTrue(TestUtils.toSet(heroFacade.findAll()).contains(hero2));

        hero1 = heroFacade.findById(hero1.getId());
        hero2 = heroFacade.findById(hero2.getId());
    }


    @AfterMethod
    public void deleteHeroes() {
        if (heroFacade.findAll().contains(hero1)) {
            heroFacade.delete(hero1.getId());
            assertFalse(TestUtils.toSet(heroFacade.findAll()).contains(hero1));
        }

        if (heroFacade.findAll().contains(hero2)) {
            heroFacade.delete(hero2.getId());
            assertFalse(TestUtils.toSet(heroFacade.findAll()).contains(hero2));
        }

        if (heroFacade.findAll().contains(newHero)) {
            heroFacade.delete(newHero.getId());
            assertFalse(TestUtils.toSet(heroFacade.findAll()).contains(newHero));
        }
    }

    @Test
    public void shouldCreateHero() throws Exception {
        heroFacade.create(newHero);
        assertEquals(heroFacade.findById(newHero.getId()), newHero);
        assertTrue(heroFacade.findAll().contains(newHero));
    }

    @Test
    public void shouldFindByHeroId() throws Exception {
        assertEquals(heroFacade.findById(hero1.getId()), hero1);
        assertEquals(heroFacade.findById(hero2.getId()), hero2);
        assertNull(heroFacade.findById(1000L));
    }

    @Test
    public void shouldFindByHeroName() throws Exception {
        assertEquals(heroFacade.findByName(hero1.getName()), hero1);
        assertEquals(heroFacade.findByName(hero2.getName()), hero2);
        assertNull(heroFacade.findByName("non-existing"));
    }

    @Test
    public void shouldFindAllHeroes() throws Exception {
        assertTrue(heroFacade.findAll().contains(hero1));
        assertTrue(heroFacade.findAll().contains(hero2));
        assertEquals(heroFacade.findAll().size(), 2);
    }

    @Test
    public void shouldUpdateHero() throws Exception {
        hero1.setName("Rambo");
        logger.info("Hero id: " + hero1.getId());
        heroFacade.update((hero1));
        assertEquals(heroFacade.findAll().size(), 2);
        assertEquals(heroFacade.findById(hero1.getId()), hero1);
    }

    @Test
    public void shouldDeleteHero() throws Exception {
        assertNotNull(heroFacade.findById(hero1.getId()));
        heroFacade.delete(hero1.getId());
        assertNull(heroFacade.findById(hero1.getId()));
        assertFalse(heroFacade.findAll().contains(hero1));
    }

    @Test
    public void shouldDeleteAllHeroes() throws Exception {
        assertTrue(heroFacade.deleteAll());
        assertEquals(heroFacade.findAll().size(), 0);
    }

    @Test
    public void shouldUpdateHeroLevel() {
        assertEquals(hero1.getLevel(), 999);
        hero1.setLevel(hero1.getLevel() + 1);
        assertEquals(hero1.getLevel(), 1000);
    }

    private HeroDTO getHeroHelper(String name) {
        HeroDTO heroDTO = new HeroDTO();
        heroDTO.setName(name);
        heroDTO.setLevel(999);
        return heroDTO;
    }

}
