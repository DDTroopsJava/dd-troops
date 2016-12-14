package cz.muni.fi.pa165.ddtroops.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by pstanko.
 * @author pstanko
 */
public class TroopCreateDTO {
    
    private String name;
    private String mission;
    private int gold;
    private Set<HeroDTO> heroes = new HashSet<>();
    
    public String getName() {
        return name;
    }

    public Set<HeroDTO> getHeroes() {
        return heroes;
    }

    public void setHeroes(Set<HeroDTO> heroes) {
        this.heroes = heroes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || !(o instanceof TroopCreateDTO))
            return false;

        TroopCreateDTO troopCreateDTO = (TroopCreateDTO) o;

        return getName() != null ? getName().equals(troopCreateDTO.getName()) : troopCreateDTO.getName() == null;

    }

    @Override
    public int hashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }
}
