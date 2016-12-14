package cz.muni.fi.pa165.ddtroops.service.services;

import cz.muni.fi.pa165.ddtroops.dto.UserDTO;
import cz.muni.fi.pa165.ddtroops.entity.Hero;
import cz.muni.fi.pa165.ddtroops.entity.Role;
import cz.muni.fi.pa165.ddtroops.entity.Troop;
import cz.muni.fi.pa165.ddtroops.entity.User;

/**
 * Created by pstanko.
 *
 * @author pstanko
 */
public class TestUtils {

    static Troop createTroop(String name, String mission, int gold) {
        return new Troop(name, mission, gold);
    }

    static Troop createTroop(String name, String mission) {
        return createTroop(name, mission, 1000);
    }

    static Troop createTroop(String name) {
        return createTroop(name, name + "'s mission");
    }

    static User createUser(String name) {
        return createUser(name, false);
    }

    static User createUser(String name, boolean admin) {
        User user = new User(name, admin);

        return user;
    }

    static Role createRole(String name) {
        return new Role(name);
    }

    static Role createRole(String name, long attackPower, long defensePower) {
        Role role = new Role(name);
        role.setAttackPower(attackPower);
        role.setDefensePower(defensePower);
        return role;
    }

    static Role createRole(String name, long attackPower) {
        return createRole(name, attackPower, 0);
    }

    static Hero createHero(String name) {
        return new Hero(name);
    }

    static Hero createHero(String name, Role role, int level) {
        Hero hero = new Hero(name);
        hero.setLevel(level);
        hero.addRole(role);
        return hero;
    }

    public static UserDTO createUserDto(String name) {
        return new UserDTO(name);
    }
}
