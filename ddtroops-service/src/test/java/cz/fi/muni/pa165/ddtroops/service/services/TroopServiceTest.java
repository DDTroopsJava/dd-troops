package cz.fi.muni.pa165.ddtroops.service.services;

import cz.fi.muni.pa165.ddtroops.dao.TroopDao;
import cz.fi.muni.pa165.ddtroops.entity.Troop;
import cz.fi.muni.pa165.ddtroops.exceptions.DDTroopsServiceException;
import cz.fi.muni.pa165.ddtroops.service.config.ServiceConfiguration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.dozer.Mapper;
import org.hibernate.service.spi.ServiceException;
import org.junit.Assert;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Richard
 */
@DirtiesContext
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TroopServiceTest extends AbstractTestNGSpringContextTests {
    
    @Mock
    private TroopDao troopDao;
    
    @Autowired
    @InjectMocks
    private TroopService troopService;
    
    @Autowired
    @Spy
    private Mapper mapper;
    
    private Troop testTroop1;
    private Troop testTroop2;
    private Troop testTroop3;
    
    private List<Troop> troops = new ArrayList<>();
    
    @BeforeMethod
    public void prepareTestUsers() {
        testTroop1 = TestUtils.createTroop("First Troop");
        testTroop2 = TestUtils.createTroop("Second Troop");
        testTroop3 = TestUtils.createTroop("Third Troop");
        
        testTroop1.setId(1L);
        testTroop2.setId(2L);
        testTroop3.setId(3L);
       
        troops = new ArrayList<>();
        
        troops.add(new Troop("root"));
        troops.add(testTroop1);
        troops.add(testTroop2);
        troops.add(testTroop3);
    }
    
    @BeforeClass
    public void setupMocks() throws ServiceException {
        MockitoAnnotations.initMocks(this);
        when(troopDao.save(any(Troop.class))).thenAnswer(invoke -> {
            
            Troop mockedTroop = invoke.getArgumentAt(0, Troop.class);
            if (mockedTroop.getId() != null){
                troops.set(mockedTroop.getId().intValue(), mockedTroop);
                return mockedTroop;
            }
            if (troopDao.findByName(mockedTroop.getName()) != null){
                throw new IllegalArgumentException("Troop alredy exists!");
            }
            mockedTroop.setId((long)troops.size());
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
            if (!optTroop.isPresent()){
                return null;
            }
            return optTroop.get();
        });

        when(troopDao.findAll()).thenAnswer(invoke -> Collections.unmodifiableList(troops));
        
        doAnswer((Answer<Void>) (InvocationOnMock invoke) -> {
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
        assertEquals((long)testTroop1.getId(), origId);
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
}
