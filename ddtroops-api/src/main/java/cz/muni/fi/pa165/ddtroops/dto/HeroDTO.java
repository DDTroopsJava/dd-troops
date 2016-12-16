package cz.muni.fi.pa165.ddtroops.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author pstanko
 */
public class HeroDTO {

    private Long id;

    private String name;

    private List<RoleDTO> roles = new ArrayList<>();

    private TroopDTO troop;

    private int level;

    public HeroDTO(String name) {
        this.name = name;
    }

    public HeroDTO() {
    }

    public HeroDTO(String name, int level) {
        this.name = name;
        this.troop = troop;
        this.level = level;
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

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    public TroopDTO getTroop() {
        return troop;
    }

    public void setTroop(TroopDTO troop) {
        this.troop = troop;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addRole(RoleDTO role)
    {
        roles.add(role);
    }

    public void removeRole(RoleDTO role)
    {
        roles.remove(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || !(o instanceof HeroDTO))
            return false;

        HeroDTO heroDTO = (HeroDTO) o;

        return getName() != null ? getName().equals(heroDTO.getName()) : heroDTO.getName() == null;

    }

    @Override
    public int hashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "HeroDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roles=" + roles +
                ", troop=" + troop +
                ", level=" + level +
                '}';
    }
}
