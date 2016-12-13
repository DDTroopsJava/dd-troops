package cz.muni.fi.pa165.ddtroops.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Peter Zaoral.
 * @author Peter Zaoral
 */
public class HeroCreateDTO {
    private String name;
    private int level;
    private Set<RoleDTO> roles = new HashSet<>();

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
