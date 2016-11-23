package cz.fi.muni.pa165.ddtroops.service.services;

import java.util.ArrayList;
import java.util.List;

import cz.fi.muni.pa165.ddtroops.dto.HeroDTO;
import cz.fi.muni.pa165.ddtroops.dto.TroopDTO;
import cz.fi.muni.pa165.ddtroops.entity.Hero;
import cz.fi.muni.pa165.ddtroops.entity.Troop;
import cz.fi.muni.pa165.ddtroops.service.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by pstanko.
 * @author pstanko
 */
@ContextConfiguration(classes = ServiceConfiguration.class)

public class BeanMappingServiceTest extends AbstractTestNGSpringContextTests
{

    @Autowired
    private BeanMappingService beanMappingService;

    private List<Troop> troops = new ArrayList<>();
    private List<Hero> heroes = new ArrayList<>();



    @BeforeMethod
    public void createTroops(){

        Troop troopScala = TestUtils.createTroop("Scala");
        Troop troopJava = TestUtils.createTroop("Java");
        Troop troopKotlin = TestUtils.createTroop("Kotlin");
        Troop troopClojure = TestUtils.createTroop("Clojure");



        Hero heroSandokan = TestUtils.createHero("Sandokan");
        troopScala.addHero(heroSandokan);
        heroes.add(heroSandokan);

        Hero heroJesus = TestUtils.createHero("Jesus");
        troopScala.addHero(heroJesus);
        heroes.add(heroJesus);



        Hero heroLinus = TestUtils.createHero("Linus");
        troopKotlin.addHero(heroLinus);
        heroes.add(heroLinus);


        Hero heroBill = TestUtils.createHero("Bill");
        troopClojure.addHero(heroBill);
        heroes.add(heroBill);


        Hero heroChuck = TestUtils.createHero("Chuck");
        troopClojure.addHero(heroChuck);
        heroes.add(heroChuck);

        troops.add(troopScala);
        troops.add(troopJava);
        troops.add(troopKotlin);
        troops.add(troopClojure);
    }

    @Test
    public void shouldMapInnerHeroes(){
        List<HeroDTO> cdtos = beanMappingService.mapTo(heroes, HeroDTO.class);
        Assert.assertEquals(cdtos.get(0).getTroop().getName(), "Scala");
    }

    @Test void shouldMapTroops() {
        List<TroopDTO> cdtos = beanMappingService.mapTo(troops, TroopDTO.class);
        Assert.assertEquals(cdtos.get(0).getHeroes().size(), 2);
    }

}