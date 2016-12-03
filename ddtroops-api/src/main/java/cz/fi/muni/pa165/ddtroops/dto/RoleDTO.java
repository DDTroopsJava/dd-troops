package cz.fi.muni.pa165.ddtroops.dto;

/**
 * @author pstanko
 */
public class RoleDTO {
    private Long id;
    private String name;
    private String description;
    private int attackPower;
    private int defensePower;

    public RoleDTO() {
    }

    public RoleDTO(String name) {
        this.name = name;
    }

    public RoleDTO(String name, String description) {
        this.name = name;
        this.description = description;
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

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || !(o instanceof RoleDTO))
            return false;

        RoleDTO roleDTO = (RoleDTO) o;

        return getName() != null ? getName().equals(roleDTO.getName()) : roleDTO.getName() == null;

    }

    @Override
    public int hashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", attackPower=" + attackPower +
                ", defensePower=" + defensePower +
                '}';
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public void setDefensePower(int defensePower) {
        this.defensePower = defensePower;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefensePower() {
        return defensePower;
    }

}
