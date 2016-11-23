package cz.fi.muni.pa165.ddtroops.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.validation.constraints.NotNull;

/**
 * Created by xgono
 * 
 * @author xgono
 */
@Entity
public class Troop implements Comparable<Troop> {
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
    
    @OneToMany(mappedBy = "troop", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Hero> heroes = new HashSet<>();

    public Troop(String name, String mission, int gold) {
        this.name = name;
        this.mission = mission;
        this.gold = gold;
    }

    public Troop(String name) {
        this.name = name;
    }

    public Troop() {
    }

    public Troop(String name, String mission) {
        this.name = name;
        this.mission = mission;
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

    public Set<Hero> getHeroes() {
        return Collections.unmodifiableSet(heroes);
    }
    
    public void addHero(Hero hero) {
        heroes.add(hero);
        if (hero != null) {
            hero.setTroopWithoutUpdate(this);
        }
    }

    public void addHeroWithoutUpdate(Hero hero) {
        heroes.add(hero);
    }

    public void removeHero(Hero hero) {
        if(hero.getTroop().equals(this)) {
            heroes.remove(hero);
            hero.setTroopWithoutUpdate(null);
        }
    }

    public long getAttackPower(){
        return heroes.stream().mapToLong(Hero::getAttackPower).sum();
    }

    public long getDefensePower(){
        return heroes.stream().mapToLong(Hero::getDefensePower).sum();
    }

    public void levelUpHeroes(){
        heroes.forEach(Hero::levelUp);
    }


    public int size() {
        return heroes.size();
    }


    @PreRemove
    private void removeHeroesFromTroop() {
        for (Hero hero : heroes) {
            hero.setTroopWithoutUpdate(null);
        }
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
        if (getName() == null) {
            if (troop.getName() != null)
                return false;
        } else if (!getName().equals(troop.getName())) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        final int prime = 37;
        int result = 1;
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        return result;
    }

    @Override
    public int compareTo(Troop troop) {
        long troopSum = troop.getDefensePower() + troop.getAttackPower();
        long thisSum = getDefensePower() + getAttackPower();
        return  (int) (thisSum - troopSum);
    }
}
