package cz.muni.fi.pa165.ddtroops.sampledata;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import cz.muni.fi.pa165.ddtroops.entity.Hero;
import cz.muni.fi.pa165.ddtroops.entity.Role;
import cz.muni.fi.pa165.ddtroops.entity.Troop;
import cz.muni.fi.pa165.ddtroops.entity.User;
import cz.muni.fi.pa165.ddtroops.service.services.HeroService;
import cz.muni.fi.pa165.ddtroops.service.services.RoleService;
import cz.muni.fi.pa165.ddtroops.service.services.TroopService;
import cz.muni.fi.pa165.ddtroops.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by pstanko.
 * @author pstanko
 */
@Component
@Transactional //transactions are handled on facade layer
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {
    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);


    @Autowired
    private UserService userService;

    @Autowired
    private HeroService heroService;

    @Autowired
    private TroopService troopService;

    @Autowired
    private RoleService roleService;

    private Map<String, User> users = new HashMap<>();
    private Map<String, Role> roles = new HashMap<>();
    private Map<String, Troop> troops = new HashMap<>();
    private Map<String, Hero> heroes = new HashMap<>();

    private void loadUsers() throws IOException
    {
        User chuck = user(
            "heslo",
            "Chuck",
            "chuck@example.com", "12345", toDate(1999, 5,4), true);
        User superman = user(
            "heslo",
            "Superman",
            "superman@example.com",
            "564321",
            toDate(2099, 5,21),
            false);
        User bill = user(
            "heslo",
            "Bill",
            "bill@microsoft.com",
            "00000",
            toDate(1991, 1,1),
            false
        );
        log.info("Loaded DDTroops users.");
    }

    private void loadHeroes() throws IOException
    {
        Hero terminator = hero("Terminator", 10);
        Hero chuck = hero("Chuck", 10000);
        Hero superman = hero("Superman", 5);
    }

    private void loadRoles() throws IOException
    {
        Role mage = role("magician", "Ultimate magician", 50, 15);
        Role tank = role("tank", "Ultimate tank", 15, 35);
        Role warrior = role("warrior", "Ultimate warrior", 30, 25);
        Role chuck = role("chuck", "Ultimate ultimate", 3000, 250000);
    }

    private void loadTroops() throws  IOException
    {
        Troop chuck = troop("Chuck", "Kill the chuck", 100000000);
        Troop alpha = troop("Alpha", "Kill the giant", 1000);
        Troop felixOrder = troop("Order of the Phoenix", "Kill Voldemort", 25000);
    }

    @Override
    public void loadData() throws IOException {
        loadUsers();
        loadHeroes();
        loadRoles();
        loadTroops();
    }

    private static Date toDate(int year, int month, int day) {
        return Date.from(LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private User user(String password, String name, String email, String phone, Date joined, boolean admin) {
        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPhone(phone);
        u.setJoinedDate(joined);
        u.setAdmin(admin);
        userService.register(u, password);
        users.put(name.toLowerCase(), u);
        return u;
    }

    private Hero hero(String name, int level){
        Hero hero = new Hero(name);
        hero.setLevel(level);
        heroService.create(hero);
        heroes.put(name.toLowerCase(), hero);
        return hero;
    }

    private Troop troop(String name, String mission, int gold){
        Troop troop = new Troop(name, mission, gold);
        troopService.create(troop);
        troops.put(name.toLowerCase(), troop);
        return troop;
    }

    private Role role(String name, String desc, int att, int def){
        Role role = new Role(name, desc);
        role.setAttackPower(att);
        role.setDefensePower(def);
        roleService.create(role);
        roles.put(name.toLowerCase(), role);
        return role;
    }
}
