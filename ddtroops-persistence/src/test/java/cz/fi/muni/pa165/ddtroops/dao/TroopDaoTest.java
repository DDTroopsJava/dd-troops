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

    private Troop troop1;
    private Troop troop2;
    private Troop troop3;

    @BeforeMethod
    public void createToops() throws Exception {
        troop1 = createTroop("Troop1", "Lol", 1000L);
        troop2 = createTroop("Troop2", "Lol", 1500L);
        troop3 = createTroop("Troop3", "Heroic", 2000L);

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
    public void shouldNotCreateExistingUser() throws Exception {
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



    private static Troop createTroop(String name, String mission, Long gold){
        Troop troop = new Troop();
        troop.setName(name);
        troop.setMission(mission);
        troop.setGold(gold);
        return troop;
    }

*/

}