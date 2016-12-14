package cz.muni.fi.pa165.ddtroops.service.services;

import cz.muni.fi.pa165.ddtroops.dao.HeroDao;
import cz.muni.fi.pa165.ddtroops.dao.RoleDao;
import cz.muni.fi.pa165.ddtroops.dao.TroopDao;
import cz.muni.fi.pa165.ddtroops.entity.Hero;
import cz.muni.fi.pa165.ddtroops.entity.Role;
import cz.muni.fi.pa165.ddtroops.entity.Troop;
import cz.muni.fi.pa165.ddtroops.service.config.ServiceConfiguration;
import cz.muni.fi.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
import org.hibernate.service.spi.ServiceException;
import org.junit.Assert;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

/**
 * @author Richard
 */
@DirtiesContext
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TroopServiceTest extends AbstractTestNGSpringContextTests {

    @Mock
    private TroopDao troopDao;

    @Mock
    private HeroDao heroDao;

    @Mock
    private RoleDao roleDao;

    @Autowired
    @InjectMocks
    private TroopService troopService;

    private Troop testTroop1;
    private Troop testTroop2;
    private Troop testTroop3;

    private Hero hero1FromTroop1;
    private Hero hero2FromTroop1;
    private Hero hero1FromTroop2;
    private Hero hero2FromTroop2;
    private Hero hero1FromTroop3;

    private Role role1;
    private Role role2;
    private Role role3;

    private List<Troop> troops = new ArrayList<>();

    @BeforeMethod
    public void prepareTestTroops() {
        testTroop1 = TestUtils.createTroop("First Troop");
        testTroop2 = TestUtils.createTroop("Second Troop");
        testTroop3 = TestUtils.createTroop("Third Troop");

        testTroop1.setId(1L);
        testTroop2.setId(2L);
        testTroop3.setId(3L);

        troops = new ArrayList<>();

        troops.add(TestUtils.createTroop("root", "NOTHING"));
        troops.add(testTroop1);
        troops.add(testTroop2);
        troops.add(testTroop3);
    }

    @BeforeClass
    public void setupMocks() throws ServiceException {
        MockitoAnnotations.initMocks(this);
        when(troopDao.save(any(Troop.class))).thenAnswer(invoke -> {

            Troop mockedTroop = invoke.getArgumentAt(0, Troop.class);
            if (mockedTroop.getId() != null) {
                troops.set(mockedTroop.getId().intValue(), mockedTroop);
                return mockedTroop;
            }
            if (troopDao.findByName(mockedTroop.getName()) != null) {
                throw new IllegalArgumentException("Troop alredy exists!");
            }
            mockedTroop.setId((long) troops.size());
            troops.add(mockedTroop);
            return mockedTroop;
        });

        when(troopDao.findOne(anyLong())).thenAnswer(invoke -> {
            int argumentAt = invoke.getArgumentAt(0, Long.class).intValue();
            if (argumentAt >= troops.size()) return null;
            return troops.get(argumentAt);
        });

        when(troopDao.findByName(anyString())).thenAnswer(invoke -> {
            String arg = invoke.getArgumentAt(0, String.class);
            Optional<Troop> optTroop = troops.stream().filter((troop) -> troop.getName().equals(arg)).findFirst();
            return optTroop.orElse(null);
        });

        when(troopDao.findAll()).thenAnswer(invoke -> Collections.unmodifiableList(troops));

        when(troopDao.findByMission(anyString())).thenAnswer(invoke -> {
            String mission = invoke.getArgumentAt(0, String.class);
            Stream<Troop> stream = troops.stream().filter(
                    t -> t.getMission().equals(mission));
            return stream.collect(Collectors.toList());
        });

        doAnswer((InvocationOnMock invoke) -> {
            Troop mockedTroop = invoke.getArgumentAt(0, Troop.class);
            if (mockedTroop.getId() == null) {
                throw new IllegalArgumentException("The troop doesn't exists!");
            } else if (mockedTroop.getId() >= troops.size()) {
                throw new IllegalArgumentException("The troop doesn't exists!");
            }
            troops.remove(mockedTroop.getId().intValue());
            return null;
        }).when(troopDao).delete(any(Troop.class));
    }

    @Test
    public void shouldUpdateExistingTroop() throws Exception {
        int origSize = troops.size();
        long origId = testTroop1.getId();
        testTroop1.setName("New name");
        troopService.update(testTroop1);
        assertEquals((long) testTroop1.getId(), origId);
        assertEquals(origSize, troops.size());

        Troop troop = troopService.findById(testTroop1.getId());
        assertEquals(troop, testTroop1);
        assertEquals(troop.getName(), "New name");
    }

    @Test(expectedExceptions = DDTroopsServiceException.class)
    public void shouldNotAddDuplicateTroop() throws Exception {
        troopService.update(new Troop("First Troop"));
    }

    @Test
    public void shouldDeleteExistingTroop() throws Exception {
        int origSize = troops.size();
        troopService.delete(testTroop1);
        assertEquals(origSize - 1, troops.size());

        Troop troop = troopService.findByName(testTroop1.getName());
        Assert.assertNull(troop);

        troop = troopService.findByName(testTroop2.getName());
        assertEquals(troop, testTroop2);

        troop = troopService.findByName(testTroop3.getName());
        assertEquals(troop, testTroop3);
    }

    @Test(expectedExceptions = DDTroopsServiceException.class)
    public void shouldNotDeleteNonExistingTroop() throws Exception {
        troopService.delete(new Troop("Test troop with nonexisting ID"));
    }

    @Test(expectedExceptions = DDTroopsServiceException.class)
    public void shouldNotDeleteTroopWithNonExistingID() throws Exception {
        Troop troop = new Troop("test");
        troop.setId(666L);
        troopService.delete(troop);
    }

    @Test
    public void shouldReturnValidUserById() throws Exception {
        assertEquals(troopService.findById(testTroop1.getId()), testTroop1);
        assertNotEquals(troopService.findById(testTroop1.getId()), testTroop2);
    }

    @Test
    public void shouldReturnNullWhenFindingByNonExistingId() throws Exception {
        Assert.assertNull(troopService.findById(1000L));
    }

    @Test
    public void shouldReturnTroopByValidName() throws Exception {
        Troop troopByName = troopService.findByName(testTroop1.getName());
        assertEquals(troopByName, testTroop1);
        assertNotEquals(troopService.findByName(testTroop1.getName()), testTroop2);
    }

    @Test
    public void shouldReturnNullWhenFindingByNonExistingName() throws Exception {
        Assert.assertNull(troopService.findByName("00000000000000000"));
    }

    // --------------------- topN Business Method tests ------------------------

    @Test
    public void testTopNWithZero() throws Exception {
        setUpDataForTopNTesting();

        assertEquals(troopService.topN(0, "POSSIBLE", 2L).size(), 0);
    }

    @Test(expectedExceptions = DDTroopsServiceException.class)
    public void testTopNWithNegative() throws Exception {
        setUpDataForTopNTesting();

        troopService.topN(-1, "POSSIBLE", 2L);
    }

    @Test
    public void testTopNWithTroopSizeZero() throws Exception {
        setUpDataForTopNTesting();

        assertEquals(troopService.topN(0, "POSSIBLE", 0L).size(), 0);
    }

    @Test
    public void testTopNWithTroopSizeNegative() throws Exception {
        setUpDataForTopNTesting();

        assertEquals(troopService.topN(0, "POSSIBLE", 0L).size(), 0);
    }

    @Test
    public void shouldReturnTroop2WithTopFirst() throws Exception {
        setUpDataForTopNTesting();

        List<Troop> topNTroops = troopService.topN(1, "POSSIBLE", 2L);
        assertEquals(topNTroops.size(), 1);
        assert (topNTroops.contains(testTroop2));
    }

    @Test
    public void shouldReturnTroop1And2WithTopTwo() throws Exception {
        setUpDataForTopNTesting();

        List<Troop> topNTroops = troopService.topN(2, "POSSIBLE", 2L);
        assertEquals(topNTroops.size(), 2);
        assert (topNTroops.contains(testTroop1));
        assert (topNTroops.contains(testTroop2));
    }

    @Test
    public void shouldReturnTroop3WithTopTwoFromOtherMission() throws Exception {
        setUpDataForTopNTesting();

        List<Troop> topNTroops = troopService.topN(2, "IMPOSSIBLE", 1L);
        assertEquals(topNTroops.size(), 1);
        assert (topNTroops.contains(testTroop3));
    }

    @Test
    public void shouldReturnOrder321WithoutMissionAndSizeFilter() throws Exception {
        setUpDataForTopNTesting();

        List<Troop> topNTroops = troopService.topN(3, null, null);
        assertEquals(topNTroops.size(), 3);
        assertEquals(topNTroops.get(0), testTroop3);
        assertEquals(topNTroops.get(1), testTroop2);
        assertEquals(topNTroops.get(2), testTroop1);
    }

    @Test
    public void shouldReturnTroop3WithoutMissionFilterAndSize1() throws Exception {
        setUpDataForTopNTesting();

        List<Troop> topNTroops = troopService.topN(3, null, 1L);
        assertEquals(topNTroops.size(), 1);
        assert (topNTroops.contains(testTroop3));
    }

    @Test
    public void shouldAbortBattle() throws Exception {
        setUpDataForTopNTesting();
        assertEquals(troopService.findByName("First Troop").getAttackPower()
                - troopService.findByName("First Troop").getAttackPower(), 0L);
        assertNull(troopService.battle(testTroop1, testTroop1));
        for (Hero hero : troopService.findByName("First Troop").getHeroes()) {
            assertEquals(hero.getLevel(), 1);
        }
    }

    @Test
    public void shouldSecondTroopWinBattle() throws Exception {
        setUpDataForTopNTesting();
        assertEquals(troopService.findByName("First Troop").getAttackPower(), 15L);
        assertEquals(troopService.findByName("Second Troop").getAttackPower(), 105L);
        assertEquals(troopService.battle(testTroop1, testTroop2), testTroop2);

        for (Hero hero : troopService.findByName("Second Troop").getHeroes()) {
            assertEquals(hero.getLevel(), 2);
        }
        for (Hero hero : troopService.findByName("First Troop").getHeroes()) {
            assertEquals(hero.getLevel(), 1);
        }
    }

    @Test
    public void shouldThirdTroopWinBattle() throws Exception {
        setUpDataForTopNTesting();
        assertEquals(troopService.findByName("Third Troop").getAttackPower(), 1000L);
        assertEquals(troopService.findByName("Third Troop").getDefensePower(), 2000L);
        assertEquals(troopService.findByName("Second Troop").getAttackPower(), 105L);
        assertEquals(troopService.findByName("Second Troop").getDefensePower(), 210L);
        assertEquals(troopService.battle(testTroop3, testTroop2), testTroop3);
        for (Hero hero : troopService.findByName("Second Troop").getHeroes()) {
            assertEquals(hero.getLevel(), 1);
        }
        for (Hero hero : troopService.findByName("Third Troop").getHeroes()) {
            assertEquals(hero.getLevel(), 11);
        }
    }

    private void setUpDataForTopNTesting() {
        testTroop1 = TestUtils.createTroop("First Troop", "POSSIBLE");
        testTroop2 = TestUtils.createTroop("Second Troop", "POSSIBLE");
        testTroop3 = TestUtils.createTroop("Third Troop", "IMPOSSIBLE");

        testTroop1.setId(1L);
        testTroop2.setId(2L);
        testTroop3.setId(3L);

        assignRoles();
        addHeroes();

        troops = new ArrayList<>();

        troops.add(TestUtils.createTroop("root", "NOTHING"));
        troops.add(testTroop1);
        troops.add(testTroop2);
        troops.add(testTroop3);
    }

    private void assignRoles() {
        when(roleDao.save(any(Role.class))).thenAnswer( invoke -> invoke.getArgumentAt(0, Role.class));

        role1 = TestUtils.createRole("Test role1", 10L, 20L);
        role2 = TestUtils.createRole("Test role2", 5L, 10L);
        role3 = TestUtils.createRole("Test role3", 100L, 200L);
    }

    private void addHeroes() {

        // should have AP = 10;
        hero1FromTroop1 = TestUtils.createHero("Hero nr. 1 from Troop nr. 1 with role1", role1, 1);
        // should have AP = 5;
        hero2FromTroop1 = TestUtils.createHero("Hero nr. 2 from Troop nr. 1 with role2", role2, 1);

        // should have AP = 5;
        hero1FromTroop2 = TestUtils.createHero("Hero nr. 1 from Troop nr. 2 with role2", role2, 1);
        // should have AP = 100;
        hero2FromTroop2 = TestUtils.createHero("Hero nr. 2 from Troop nr. 2 with role3", role3, 1);

        // should have AP = 1000;
        hero1FromTroop3 = TestUtils.createHero("Hero nr. 1 from Troop nr. 3 with role4", role3, 10);

        when(heroDao.save(any(Hero.class))).thenAnswer( invoke -> invoke.getArgumentAt(0, Hero.class));

        // troop1 should have AP = 15
        testTroop1.addHero(hero1FromTroop1);
        testTroop1.addHero(hero2FromTroop1);

        // troop2 should have AP = 105
        testTroop2.addHero(hero1FromTroop2);
        testTroop2.addHero(hero2FromTroop2);

        // troop3 should have AP = 1000
        testTroop3.addHero(hero1FromTroop3);
    }
}
