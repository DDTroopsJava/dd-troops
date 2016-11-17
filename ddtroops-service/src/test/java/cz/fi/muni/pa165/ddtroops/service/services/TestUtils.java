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
        return new User(name);
    }

    public static User createUser(String name, boolean admin){
        return new User(name, admin);
    }

    public static Role createRole(String name){
        return new Role(name);
    }

    public static Hero createHero(String name){
        return new Hero(name);
    }


    public static UserDTO createUserDto(String name){
        return new UserDTO(name);
    }
}
