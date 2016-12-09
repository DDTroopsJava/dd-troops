package cz.muni.fi.pa165.ddtroops.dto;

/**
 * Created by pstanko.
 * @author pstanko
 */
public class UserLoginDTO {
    String email;
    String password;

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
