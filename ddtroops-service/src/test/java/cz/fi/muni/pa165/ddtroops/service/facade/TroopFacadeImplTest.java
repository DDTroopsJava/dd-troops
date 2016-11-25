/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.fi.muni.pa165.ddtroops.service.facade;

import cz.fi.muni.pa165.ddtroops.dto.TroopDTO;
import cz.fi.muni.pa165.ddtroops.facade.TroopFacade;
import cz.fi.muni.pa165.ddtroops.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static cz.fi.muni.pa165.ddtroops.service.facade.TestUtils.toSet;
import static org.testng.Assert.*;

/**
 * @author xgono
 */
@DirtiesContext
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TroopFacadeImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TroopFacade troopFacade;

    private TroopDTO testTroop1;
    private TroopDTO testTroop2;


    @BeforeMethod
    public void createTestTroops() {
        testTroop1 = new TroopDTO("Test 1", "Mission Test", 10);
        testTroop2 = new TroopDTO("Test 2", "Mission Test", 20);

        troopFacade.update(testTroop1);
        assertTrue(toSet(troopFacade.findAll()).contains(testTroop1));

        troopFacade.update(testTroop2);
        assertTrue(toSet(troopFacade.findAll()).contains(testTroop2));

        testTroop1 = troopFacade.findById(testTroop1.getId());
        testTroop2 = troopFacade.findById(testTroop2.getId());
    }

    @AfterMethod
    public void deleteTestTroops() {
        if (troopFacade.findAll().contains(testTroop1)) {
            troopFacade.delete(testTroop1);
            assertFalse(toSet(troopFacade.findAll()).contains(testTroop1));
        }

        if (troopFacade.findAll().contains(testTroop2)) {
            troopFacade.delete(testTroop2);
            assertFalse(toSet(troopFacade.findAll()).contains(testTroop2));
        }
    }

    @Test
    public void testFindById() throws Exception {
        assertEquals(troopFacade.findById(testTroop1.getId()), testTroop1);
        assertEquals(troopFacade.findById(testTroop2.getId()), testTroop2);
        assertNull(troopFacade.findById(666L));
    }

    @Test
    public void testFindByName() throws Exception {
        assertEquals(troopFacade.findByName(testTroop1.getName()), testTroop1);
        assertEquals(troopFacade.findByName(testTroop2.getName()), testTroop2);
        assertNull(troopFacade.findByName("THIS NAME DOES NOT EXIST"));
    }

    @Test
    public void testUpdate() throws Exception {
        testTroop1.setGold(999);
        logger.info("Troop id: " + testTroop1);
        troopFacade.update(testTroop1);
        assertEquals(troopFacade.findAll().size(), 2);
        assertEquals(troopFacade.findById(testTroop1.getId()), testTroop1);
        assertEquals(troopFacade.findById(testTroop1.getId()).getGold(), 999);
    }

    @Test
    public void testFindAll() throws Exception {
        assertTrue(troopFacade.findAll().contains(testTroop1));
        assertTrue(troopFacade.findAll().contains(testTroop2));
    }

    @Test
    public void testDelete() throws Exception {
        assertNotNull(troopFacade.findById(testTroop1.getId()));
        troopFacade.delete(testTroop1);
        assertNull(troopFacade.findById(testTroop1.getId()));
        assertFalse(troopFacade.findAll().contains(testTroop1));
    }
}
