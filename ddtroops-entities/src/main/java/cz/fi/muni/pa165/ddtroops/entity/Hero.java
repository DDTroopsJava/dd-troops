package cz.fi.muni.pa165.ddtroops.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 *
 * @author pstanko
 */
@Entity
public class Hero {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable=false,unique=true)
    private String name;

    @ManyToMany
    private Set<Role> roles;

    @ManyToOne
    private Group group;

    @Column(nullable = false)
    private int experienceLevel;



    public Long getId() {
        return id;
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

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(int experienceLevel) {
        this.experienceLevel = experienceLevel;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Hero)) return false;

        Hero hero = (Hero) o;

        return getName().equals(hero.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
