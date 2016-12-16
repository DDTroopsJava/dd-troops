/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.ddtroops.service.facade;

import static org.testng.Assert.*;

import cz.muni.fi.pa165.ddtroops.dto.HeroDTO;
import cz.muni.fi.pa165.ddtroops.dto.HeroUpdateDTO;
import cz.muni.fi.pa165.ddtroops.dto.RoleDTO;
import cz.muni.fi.pa165.ddtroops.dto.TroopCreateDTO;
import cz.muni.fi.pa165.ddtroops.dto.TroopDTO;
import cz.muni.fi.pa165.ddtroops.dto.TroopUpdateDTO;
import cz.muni.fi.pa165.ddtroops.facade.HeroFacade;
import cz.muni.fi.pa165.ddtroops.facade.RoleFacade;
import cz.muni.fi.pa165.ddtroops.facade.TroopFacade;
import cz.muni.fi.pa165.ddtroops.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author xgono
 */
@DirtiesContext
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TroopFacadeImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TroopFacade troopFacade;

    @Autowired
    private HeroFacade heroFacade;

    @Autowired
    private RoleFacade roleFacade;

    private TroopDTO troop1;
    private TroopDTO troop2;

    private HeroDTO hero1;
    private HeroDTO hero2;
    private RoleDTO role1;
    private RoleDTO role2;
    private RoleDTO role3;
    private HeroDTO hero3;


    @BeforeMethod
    public void setupTest(){
        setupRoles();
        setupHeroes();
        setupTroops();
    }

    private HeroDTO setupHero(String name, int level)
    {
        HeroDTO hero = TestUtils.createHero(name, level);
        heroFacade.create(hero);
        assertTrue(heroFacade.findAll().contains(hero));
        return heroFacade.findById(hero.getId());
    }

    private RoleDTO setupRole(String name, int att, int def)
    {
        RoleDTO role = TestUtils.createRole(name, att, def);
        roleFacade.create(role);
        assertTrue(roleFacade.findAll().contains(role));
        return roleFacade.findById(role.getId());
    }

    private TroopDTO setupTroop(String name)
    {
        TroopDTO troop = TestUtils.createTroop(name);
        TroopDTO createdTroop = troopFacade.create(getCreateTroop(troop));
        assertTrue(troopFacade.findAll().contains(troop));
        return troopFacade.findById(createdTroop.getId());
    }


    private void setupRoles() {
        role1 = setupRole("Magician", 10, 20);
        role2 = setupRole("Guard", 5, 50);
        role3 = setupRole("Elf", 15, 10);
    }

    private void setupHeroes(){
        hero1 = setupHero("Chuck", 100);
        hero2 = setupHero("Superman", 1);
        hero3 = setupHero("Batman", 5);
    }

    private void setupTroops() {
        troop1 = setupTroop("Test 1");
        troop2 = setupTroop("Test 2");
    }

    private void deleteRoles()
    {
        roleFacade.delete(role1.getId());
        roleFacade.delete(role2.getId());
        roleFacade.delete(role3.getId());
    }

    private void deleteHeroes()
    {
        heroFacade.delete(hero1.getId());
        heroFacade.delete(hero2.getId());
        heroFacade.delete(hero3.getId());
    }

    private void deleteTroops()
    {
        if (troopFacade.findAll().contains(troop1)) {
            troopFacade.delete(troop1.getId());
            assertFalse(troopFacade.findAll().contains(troop1));
        }

        if (troopFacade.findAll().contains(troop2)) {
            troopFacade.delete(troop2.getId());
            assertFalse(troopFacade.findAll().contains(troop2));
        }
    }

    @AfterMethod
    public void deleteTestTroops() {
        deleteRoles();
        deleteHeroes();
        deleteTroops();
    }

    @Test
    public void testFindById() throws Exception {
        assertEquals(troopFacade.findById(troop1.getId()), troop1);
        assertEquals(troopFacade.findById(troop2.getId()), troop2);
        assertNull(troopFacade.findById(666L));
    }

    @Test
    public void testFindByName() throws Exception {
        assertEquals(troopFacade.findByName(troop1.getName()), troop1);
        assertEquals(troopFacade.findByName(troop2.getName()), troop2);
        assertNull(troopFacade.findByName("THIS NAME DOES NOT EXIST"));
    }

    @Test
    public void testUpdate() throws Exception {
        int sizeBefore = troopFacade.findAll().size();
        troop1.setGold(999);
        logger.info("Troop id: " + troop1);
        troopFacade.update(getUpdateTroop(troop1));
        assertEquals(troopFacade.findAll().size(), sizeBefore);
        assertEquals(troopFacade.findById(troop1.getId()), troop1);
        assertEquals(troopFacade.findById(troop1.getId()).getGold(), 999);
    }
    
    @Test
    public void testCreate() throws Exception {
        TroopDTO troop = TestUtils.createTroop("CREATE TEST");
        
        troopFacade.create(getCreateTroop(troop));
        assertTrue(troopFacade.findAll().contains(troop));
    }

    @Test
    public void testFindAll() throws Exception {
        assertTrue(troopFacade.findAll().contains(troop1));
        assertTrue(troopFacade.findAll().contains(troop2));
    }

    @Test
    public void testDelete() throws Exception {
        assertNotNull(troopFacade.findById(troop1.getId()));
        troopFacade.delete(troop1.getId());
        assertNull(troopFacade.findById(troop1.getId()));
        assertFalse(troopFacade.findAll().contains(troop1));
    }

    private void setupBattle()
    {
        hero1.getRoles().add(role1);
        hero2.getRoles().add(role2);
        hero3.getRoles().add(role3);

        hero1 = heroFacade.update((hero1));
        hero2 = heroFacade.update((hero2));
        hero3 = heroFacade.update((hero3));

        troop1.getHeroes().add(hero1);
        troop1.getHeroes().add(hero2);
        troop2.getHeroes().add(hero3);

        troop1 = troopFacade.update(getUpdateTroop(troop1));
        troop2 = troopFacade.update(getUpdateTroop(troop2));
    }

    @Test
    public void testBattle() throws Exception
    {
        setupBattle();
        assertEquals(troopFacade.battle(troop1, troop2), troop1);
        assertEquals(heroFacade.findById(hero1.getId()).getLevel(), 101);
    }

    @Test
    public void testTopN() throws Exception
    {
        setupBattle();
        assertEquals(troopFacade.topN(1, null, null).get(0), troop1);
    }
    
    private TroopCreateDTO getCreateTroop(TroopDTO troop)
    {
        TroopCreateDTO troopDTO = new TroopCreateDTO();
        troopDTO.setName(troop.getName());
        troopDTO.setMission(troop.getMission());
        troopDTO.setGold(troop.getGold());
        troopDTO.setHeroes(troop.getHeroes());
        return troopDTO;
    }

    private TroopUpdateDTO getUpdateTroop(TroopDTO troop){
        TroopUpdateDTO troopDTO = new TroopUpdateDTO();
        troopDTO.setId(troop.getId());
        troopDTO.setName(troop.getName());
        troopDTO.setMission(troop.getMission());
        troopDTO.setGold(troop.getGold());
        troopDTO.setHeroes(troop.getHeroes());
        return troopDTO;
    }
    
    private HeroUpdateDTO getUpdateHeroHelper(HeroDTO hero) {
        HeroUpdateDTO heroDTO = new HeroUpdateDTO();
        heroDTO.setId(hero.getId());
        heroDTO.setName(hero.getName());
        heroDTO.setLevel(hero.getLevel());
        heroDTO.setRoles(hero.getRoles());

        return heroDTO;
    }
}
