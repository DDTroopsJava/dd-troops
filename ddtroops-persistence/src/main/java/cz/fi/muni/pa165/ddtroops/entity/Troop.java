package cz.fi.muni.pa165.ddtroops.entity;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Created by xgono
 * 
 * @author xgono
 */
@Entity
public class Troop {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(nullable = false)
    private String mission;
    
    @Column(nullable = false)
    private int gold;
    
    @OneToMany(mappedBy = "troop")
    private Set<Hero> heroes = new HashSet<>();


    public Long getId() {
        return id;
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

    public Set<Hero> getHeroes() {
        return heroes;
    }
    
    public void addHero(Hero hero) {
        heroes.add(hero);
    }

    public void setHeroes(Set<Hero> heroes) {
        this.heroes = heroes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) 
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Troop))
            return false;
        
        Troop troop = (Troop)obj;
        return getName().equals(troop.getName());
    }
    
    @Override
    public int hashCode() {
        return getName().hashCode();
    }
    
    
}
