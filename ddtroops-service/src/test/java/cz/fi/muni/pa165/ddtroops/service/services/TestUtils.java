package cz.fi.muni.pa165.ddtroops.service.services;

import cz.fi.muni.pa165.ddtroops.dto.UserDTO;
import cz.fi.muni.pa165.ddtroops.entity.Hero;
import cz.fi.muni.pa165.ddtroops.entity.Role;
import cz.fi.muni.pa165.ddtroops.entity.Troop;
import cz.fi.muni.pa165.ddtroops.entity.User;

/**
 * Created by pstanko.
 * @author pstanko
 */
public class TestUtils {

    public static Troop createTroop(String name, String mission, int gold){
        return new Troop(name, mission, gold);
    }

    public static Troop createTroop(String name, String mission){
        return createTroop(name, mission, 1000);
    }

    public static Troop createTroop(String name){
        return createTroop(name, name +"'s mission");
    }

    public static User createUser(String name){
        return createUser(name, false);
    }

    public static User createUser(String name, boolean admin){
        User user = new User(name, admin);

        return user;
    }

    public static Role createRole(String name){
        return new Role(name);
    }
    
    public static Role createRole(String name, long attackPower){
        Role role = new Role(name);
        role.setAttackPower(attackPower);
        return role;
    }

    public static Hero createHero(String name){
        return new Hero(name);
    }

    public static Hero createHero(String name, Role role, int level){
        Hero hero = new Hero(name);
        hero.setLevel(level);
        hero.addRole(role);
        return hero;
    }

    public static UserDTO createUserDto(String name){
        return new UserDTO(name);
    }
}
