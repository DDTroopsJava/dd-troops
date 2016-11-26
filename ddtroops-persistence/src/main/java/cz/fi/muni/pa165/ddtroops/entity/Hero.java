package cz.fi.muni.pa165.ddtroops.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author P. Zaoral
 */
@Entity
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotNull
    private String name;

    @ManyToMany(mappedBy = "heroes", cascade =
            {CascadeType.PERSIST, CascadeType.MERGE})

    private Set<Role> roles = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "troopId")
    private Troop troop;

    private int level;

    public Hero(String name) {
        this.name = name;
    }

    public Hero() {
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

    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public void addRoleWithoutUpdate(Role role) {
        roles.add(role);
    }


    public long getAttackPower() {
        return roles.stream().mapToLong(Role::getAttackPower).sum() * level;
    }

    public long getDefensePower() {
        return roles.stream().mapToLong(Role::getDefensePower).sum() * level;
    }

    public long levelUp() {
        return ++level;
    }


    public void addRole(Role role) {
        addRoleWithoutUpdate(role);
        if (role != null) {
            role.addHeroWithoutUpdate(this);
        }
    }

    public boolean removeRole(Role role) {
        role.removeHeroWithoutUpdate(this);
        return removeRoleWithoutUpdate(role);
    }

    public boolean removeRoleWithoutUpdate(Role role) {
        return roles.remove(role);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int experience) {
        this.level = experience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || !(o instanceof Hero)) return false;

        Hero hero = (Hero) o;

        return name.equals(hero.getName());

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public Troop getTroop() {
        return troop;
    }

    public void setTroop(Troop troop) {

        if (troop != null) {
            troop.addHeroWithoutUpdate(this);
        } else if (this.troop != null) {
            this.troop.removeHero(this);
        }
        setTroopWithoutUpdate(troop);
    }

    public void setTroopWithoutUpdate(Troop troop) {
        this.troop = troop;
    }

    @PreUpdate
    @PrePersist
    public void updateRolesAndTroop()
    {
        for (Role role : roles) {
            role.addHero(this);
        }
        if(troop != null) {
            troop.addHero(this);
        }
    }

    @PreRemove
    public void removeRolesAndTroop() {
        for (Role role : roles) {
            role.removeHero(this);
        }
        if (troop != null) {
            troop.removeHero(this);
        }
    }

}