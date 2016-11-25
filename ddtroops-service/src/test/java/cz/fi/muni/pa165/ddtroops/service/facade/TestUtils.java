package cz.fi.muni.pa165.ddtroops.service.facade;

import cz.fi.muni.pa165.ddtroops.dto.HeroDTO;
import cz.fi.muni.pa165.ddtroops.dto.RoleDTO;
import cz.fi.muni.pa165.ddtroops.dto.TroopDTO;

import java.util.HashSet;
import java.util.Set;

/**
 * @author pstanko
 */
public class TestUtils {
    static <T> Set<T> toSet(Iterable<T> iterable) {
        Set<T> result = new HashSet<>();
        for (T item : iterable) {
            result.add(item);
        }
        return result;
    }

    static TroopDTO createTroop(String name){
        return new TroopDTO(name, name + "'s mission", 100);
    }

    static HeroDTO createHero(String name, int level) {
        HeroDTO heroDTO = new HeroDTO();
        heroDTO.setName(name);
        heroDTO.setLevel(level);
        return heroDTO;
    }


    static RoleDTO createRole(String name, int attack, int defense)
    {
        RoleDTO role = new RoleDTO(name);
        role.setDescription(name +"'s description!");
        role.setAttackPower(attack);
        role.setDefensePower(defense);
        return role;
    }
}
