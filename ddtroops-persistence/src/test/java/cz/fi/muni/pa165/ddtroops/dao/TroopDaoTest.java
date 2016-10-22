package cz.fi.muni.pa165.ddtroops.dao;

import cz.fi.muni.pa165.ddtroops.PersistenceSampleApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author pstanko
 */
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class TroopDaoTest extends AbstractTestNGSpringContextTests {

/*
    @Autowired
    private TroopDao troopDao;

    @Autowired
    private HeroDao heroDao;

    private Troop troop1;
    private Troop troop2;
    private Troop troop3;

    private Hero hero1;
    private Hero hero2;
    private Hero hero3;
    private Hero unassignedHero;

    @BeforeMethod
    public void createToops() throws Exception {
        hero1 = createHero("Kunigunda");
        hero2 = createHero("Shakira");
        hero3 = createHero("Chuck Norris");
        unassignedHero = createHero("The Rock");

        heroDao.create(hero1);
        heroDao.create(hero2);
        heroDao.create(hero3);
        heroDao.create(unassignedHero);

        troop1 = createTroop("Troop1", "Lol", 1000L);
        troop2 = createTroop("Troop2", "Lol", 1500L);
        troop3 = createTroop("Troop3", "Heroic", 2000L);

        troop1.addHero(hero1);
        troop2.addHero(hero2);
        troop3.addHero(hero3);

        troopDao.create(troop1);
        troopDao.create(troop2);
        troopDao.create(troop3);
    }

    @Test
    public void shouldCreateNewTroop() throws Exception {
        Troop troop = createTroop("NewTroop", "Heroic", 2500L);
        Troop created = troopDao.create(troop);

        assertEquals(troopDao.listAll().size(), 4);
        assertTrue(troopDao.listAll().contains(created));
        assertEquals(troopDao.getById(created.getId()), troop);
    }


    @Test(expectedExceptions = JpaSystemException.class)
    public void shouldNotCreateExistingTroop() throws Exception {
        Troop troop = createTroop("Troop1", "Heroic", 2500L);
        Troop created = troopDao.create(troop);

        assertEquals(troopDao.listAll().size(), 3);
        assertTrue(troopDao.listAll().contains(created));
        assertEquals(troopDao.getById(created.getId()), troop);
    }

    @Test
    public void shouldUpdateTroop2() throws Exception {
        Troop troop = troopDao.getById(troop2.getId());
        troop.setGold(0);
        troop.setMission("New Mission");
        troopDao.update(troop);

        assertTrue(troopDao.listAll().contains(troop));
        Troop updatedTroop = troopDao.getById(troop.getId());
        assertEquals(updatedTroop, troop);
        assertEquals(updatedTroop.getGold(), 0);
        assertEquals(updatedTroop.getMission(), "New Mission");
    }

    @Test
    public void shouldDeleteTroop() throws Exception {
        troopDao.delete(troop3);

        assertEquals(troopDao.listAll().size(), 2);
        assertTrue(troopDao.listAll().contains(troop1));
        assertTrue(troopDao.listAll().contains(troop2));
        assertFalse(troopDao.listAll().contains(troop3));
    }

    @Test
    public void shouldGetByIdTroop1() throws Exception {
        Troop troop = troopDao.getById(troop1.getId());
        assertEquals(troop1, troop);
        assertEquals(troop.getName(), "Troop1");
        assertEquals(troop.getMission(), "Lol");
        assertEquals(troop.getGold(), 1000L);
    }

    @Test
    public void shouldGetNullForNonExistingTroopId() throws Exception {
        Troop troop = troopDao.getById(1000);
        assertEquals(troop, null);
    }

    @Test
    public void shouldGetByNameTroop1() throws Exception {
        Troop troop = troopDao.getByName(troop1.getName());
        assertEquals(troop1, troop);
        assertEquals(troop.getName(), "Troop1");
        assertEquals(troop.getMission(), "Lol");
        assertEquals(troop.getGold(), 1000L);
    }

    @Test
    public void shouldGetNullForNonExistingTroopName() throws Exception {
        Troop troop = troopDao.getByName("Non existing name");
        assertEquals(troop, null);
    }

    @Test
    public void shouldAddHeroToTroop1() throws Exception
    {
        Troop troop = troopDao.getById(troop1.getId());
        troop.addHero(unassignedHero);
        troopDao.update(troop);
        Troop result = troopDao.getById(troop.getId());
        assertTrue(result.getHeroes().contains(unassignedHero));
        assertTrue(result.getHeroes().contains(hero1));
        assertTrue(result.getHeroes().size(), 2);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void shouldNotAddHero2ToTroop1()
    {
        Troop troop = troopDao.getById(troop1.getId());
        troop.addHero(hero2);
        troopDao.update(troop);
    }

    private static Hero createHero(String name){
        Hero hero = new Hero();
        hero.setName(name);
        hero.setExperience(1000);
        return hero;
    }

    private static Troop createTroop(String name, String mission, Long gold){
        Troop troop = new Troop();
        troop.setName(name);
        troop.setMission(mission);
        troop.setGold(gold);
        return troop;
    }*/

}