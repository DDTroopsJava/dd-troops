package cz.fi.muni.pa165.ddtroops.dto;

/**
 * @author pstanko
 */
public class RoleDTO {
    public RoleDTO() {
    }

    private Long id;
    private String name;
    private String description;

    public void setId(Long id) {
        this.id = id;
    }

    public RoleDTO(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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
            '}';
    }
}
