package cz.fi.muni.pa165.ddtroops.dto;

/**
 * Created by pstanko.
 * @author pstanko
 */
public class UserUpdateDTO extends UserCreateDTO {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
