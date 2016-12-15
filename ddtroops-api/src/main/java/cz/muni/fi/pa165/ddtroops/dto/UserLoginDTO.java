package cz.muni.fi.pa165.ddtroops.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by pstanko.
 * @author pstanko
 */
public class UserLoginDTO {
    @NotNull
    @Size(min = 3, max = 150)
    private String email;
    @NotNull
    @Size(min = 5, max = 150)
    private String password;

    public UserLoginDTO() {}

    public UserLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
