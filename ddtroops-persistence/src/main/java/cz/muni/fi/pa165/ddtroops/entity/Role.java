package cz.muni.fi.pa165.ddtroops.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author P. Kolacek <xkolac11>
 */
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column
    private long attackPower;

    @Column
    private long defensePower;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "Role_Hero",
            joinColumns = {
                    @JoinColumn(
                            name = "role_id",
                            referencedColumnName = "id"
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "hero_id",
                            referencedColumnName = "id"
                    )
            }
    )

    private Set<Hero> heroes = new HashSet<>();

    public Role(String name) {
        this.name = name;
        this.description = name + "'s sample description!";
    }

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Role() {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String name) {
        this.description = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Role))
            return false;
        Role role = (Role) obj;
        if (name == null) {
            if (role.getName() != null)
                return false;
        } else if (!name.equals(role.getName()))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int result = 1;
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        return result;
    }

    @PreUpdate
    @PrePersist
    public void updateHeroes()
    {
        for (Hero hero : heroes) {
            hero.addRole(this);
        }
    }

    @PreRemove
    public void removeHeroes() {
        for (Hero hero : heroes) {
            hero.removeRole(this);
        }
    }

    public Set<Hero> getHeroes() {
        return Collections.unmodifiableSet(heroes);
    }

    public Hero addHeroWithoutUpdate(Hero h) {
        heroes.add(h);
        return h;
    }

    public Hero addHero(Hero h) {
        addHeroWithoutUpdate(h);
        if (h != null) {
            h.addRoleWithoutUpdate(this);
        }
        return h;
    }

    public Hero removeHeroWithoutUpdate(Hero h) {
        heroes.remove(h);
        return h;

    }

    public Hero removeHero(Hero h) {
        if (h != null) {
            h.removeRoleWithoutUpdate(this);
        }
        removeHeroWithoutUpdate(h);
        return h;
    }

    public long getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(long attackPower) {
        this.attackPower = attackPower;
    }

    public long getDefensePower() {
        return defensePower;
    }

    public void setDefensePower(long defensePower) {
        this.defensePower = defensePower;
    }
}
