package cz.fi.muni.pa165.ddtroops.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * @author pstanko
 */
public class TroopDTO {

    private Long id;
    private String name;
    private String mission;
    private int gold;
    private Set<HeroDTO> heroes = new HashSet<>();

    public TroopDTO(String name, String mission, int gold) {
        this.name = name;
        this.mission = mission;
        this.gold = gold;
    }

    public TroopDTO(String name) {
        this.name = name;
    }

    public TroopDTO(String name, String mission) {
        this.name = name;
        this.mission = mission;
    }

    public TroopDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
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

    public Set<HeroDTO> getHeroes() {
        return heroes;
    }

    public void setHeroes(Set<HeroDTO> heroes) {
        this.heroes = heroes;
    }

    public void addHero(HeroDTO hero)
    {
        heroes.add(hero);
    }

    public void removeHero(HeroDTO hero)
    {
        heroes.remove(hero);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || !(o instanceof TroopDTO))
            return false;

        TroopDTO troopDTO = (TroopDTO) o;

        return getName() != null ? getName().equals(troopDTO.getName()) : troopDTO.getName() == null;

    }

    @Override
    public int hashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }
}
