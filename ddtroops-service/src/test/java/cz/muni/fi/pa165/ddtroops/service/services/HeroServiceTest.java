package cz.muni.fi.pa165.ddtroops.service.services;

import cz.muni.fi.pa165.ddtroops.dao.HeroDao;
import cz.muni.fi.pa165.ddtroops.entity.Hero;
import cz.muni.fi.pa165.ddtroops.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
import org.hibernate.service.spi.ServiceException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;


/**
 * Created by Peter Zaoral.
 *
 * @author Peter Zaoral.
 */
@DirtiesContext
@ContextConfiguration(classes = ServiceConfiguration.class)
public class HeroServiceTest extends AbstractTestNGSpringContextTests {
    @Mock
    private HeroDao heroDao;

    @Autowired
    @InjectMocks
    private HeroService heroService;


    private Hero testHero;
    private Hero testHero2;

    private List<Hero> heroes = new ArrayList<>();

    @BeforeMethod
    public void prepareTestHeroes() {
        heroes.clear();
        testHero = TestUtils.createHero("Chuck");
        testHero2 = TestUtils.createHero("Donald");
        heroes = new ArrayList<>();

        testHero.setId(1L);
        testHero2.setId(2L);

        heroes.add(new Hero("Batman"));
        heroes.add(testHero);
        heroes.add(testHero2);

        for (long i = 3L; i <= 5L; i++) {
            Hero hero = TestUtils.createHero("user" + 1);
            hero.setId(i);
            heroes.add(hero);
        }
    }

    @BeforeClass
    public void setupMocks() throws ServiceException {
        MockitoAnnotations.initMocks(this);
        when(heroDao.save(any(Hero.class))).thenAnswer(invoke -> {
            Hero mockedHero = invoke.getArgumentAt(0, Hero.class);
            if (mockedHero.getId() != null) {
                heroes.set(mockedHero.getId().intValue(), mockedHero);
                return mockedHero;
            }
            if (heroDao.findByName(mockedHero.getName()) != null) {
                throw new IllegalArgumentException("Hero already exists!");
            }
            mockedHero.setId((long) heroes.size());
            heroes.add(mockedHero);
            return mockedHero;
        });

        when(heroDao.findOne(anyLong())).thenAnswer(invoke -> {

            int argumentAt = invoke.getArgumentAt(0, Long.class).intValue();
            if (argumentAt >= heroes.size()) return null;
            return heroes.get(argumentAt);
        });

        when(heroDao.findByName(anyString())).thenAnswer(invoke -> {
            String arg = invoke.getArgumentAt(0, String.class);
            Optional<Hero> optHero = heroes.stream().filter((user) -> user.getName().equals(arg)).findFirst();
            return optHero.orElse(null);
        });

        doAnswer(invoke -> {
            Hero mockedHero = invoke.getArgumentAt(0, Hero.class);
            heroes.remove(mockedHero);
            logger.debug("Removing hero: " + mockedHero);
            return mockedHero;
        }).when(heroDao).delete(any(Hero.class));

        when(heroDao.findAll()).thenAnswer(invoke -> Collections.unmodifiableList(heroes));

        doAnswer(invoke -> {
            heroes.clear();
            return heroes;
        }).when(heroDao).deleteAll();
    }

    @Test
    public void shouldCreateNewHero() throws Exception {
        int origSize = heroes.size();
        Hero newHero = TestUtils.createHero("new_hero");
        heroService.createHero(newHero);
        assertEquals(heroes.size(), origSize + 1);
        assertEquals(newHero.getId().intValue(), heroes.size() - 1); // should have next id
        assertTrue(heroes.contains(newHero));
    }

    @Test(expectedExceptions = {DDTroopsServiceException.class})
    public void shouldNotRegisterExistingHero() throws Exception {
        Hero newHero = TestUtils.createHero("new_hero");
        Hero newHero2 = TestUtils.createHero("new_hero");
        heroService.createHero(newHero);
        heroService.createHero(newHero2);
    }

    @Test
    public void shouldUpdateExistingHero() throws Exception {
        int origSize = heroes.size();
        long origId = testHero.getId();
        testHero.setName("New name");

        heroService.updateHero(testHero);
        assertEquals((long) testHero.getId(), origId);
        assertEquals(origSize, heroes.size());
        Hero hero = heroService.findById(testHero.getId());
        assertEquals(hero, testHero);
        assertEquals(hero.getName(), "New name");
    }

    @Test
    public void shouldFindAllHeroes() throws Exception {
        assertEquals(heroService.findAll().size(), 6);
    }

    @Test
    public void shouldReturnValidHeroById() throws Exception {
        assertEquals(heroService.findById(testHero.getId()), testHero);
        assertNotEquals(heroService.findById(testHero.getId()), testHero2);
    }


    @Test
    public void shouldReturnNullWhenNonExistingHeroId() throws Exception {
        assertNull(heroService.findById(1000L));
    }

    @Test
    public void shouldReturnValidHeroByName() throws Exception {
        Hero heroByName = heroService.findByName(testHero.getName());
        assertEquals(heroByName, testHero);
        assertNotEquals(heroService.findByName(testHero.getName()), testHero2);
    }

    @Test
    public void shouldReturnNullWhenNonExistingUserName() throws Exception {
        assertNull(heroService.findByName("non-existing"));
    }

    @Test
    public void shouldRemoveHero() throws Exception {
        assertTrue(heroes.contains(testHero));
        heroService.deleteHero(testHero);
        assertFalse(heroes.contains(testHero));
    }

    @Test
    public void shouldRemoveAllHeroes() throws Exception {
        assertTrue(heroService.deleteAllHeroes());
        assertEquals(heroService.findAll().size(), 0);
    }
}
