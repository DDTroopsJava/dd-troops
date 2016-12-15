package cz.muni.fi.pa165.ddtroops.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by pstanko.
 * @author pstanko
 */
public class UserUpdatePassDTO {
    private Long id;
    @NotNull
    @Size(min = 5, max = 150)
    private String currentPassword;
    @NotNull
    @Size(min = 5, max = 150)
    private String newPassword;

    public UserUpdatePassDTO(Long id, String current, String newPassword) {
        this.id = id;
        this.currentPassword = current;
        this.newPassword = newPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
