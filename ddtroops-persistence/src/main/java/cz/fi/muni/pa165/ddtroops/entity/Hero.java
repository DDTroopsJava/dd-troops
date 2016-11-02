package cz.fi.muni.pa165.ddtroops.entity;

import java.util.HashSet;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 *
 * @author P. Zaoral
 */
@Entity
public class Hero {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false,unique=true)
    @NotNull
    private String name;

    @ManyToMany
    private Set<Role> roles = new HashSet<>();

    private int experience;

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
        return roles;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public Boolean removeRole(Role role) {
        return roles.remove(role);
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
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
}