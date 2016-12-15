package cz.muni.fi.pa165.ddtroops.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by pstanko.
 * @author pstanko
 */
public class TroopCreateDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private String name;
    @NotNull
    @Size(min = 3, max = 150)
    private String mission;
    @NotNull
    @Min(0)
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
