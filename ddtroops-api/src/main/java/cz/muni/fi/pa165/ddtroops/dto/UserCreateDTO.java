package cz.muni.fi.pa165.ddtroops.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by pstanko.
 * @author pstanko
 */
public class UserCreateDTO {

    @NotNull
    @Size(min = 5, max = 150)
    private String password;
    @NotNull
    @Size(min = 3, max = 150)
    private String email;
    @NotNull
    @Size(min = 3, max = 50)
    private String name;
    @NotNull
    @Size(min = 3, max = 150)
    private String phone;
    private Boolean admin;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
