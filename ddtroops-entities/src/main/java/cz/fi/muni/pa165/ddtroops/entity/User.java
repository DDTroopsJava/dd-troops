package cz.fi.muni.pa165.ddtroops.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 *
 * @author pstanko
 */
@Entity(name="Users")
//In Derby, its forbiden to 'USER' is reserved keyword, we need to rename table
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String passwordHash;

    @Column(nullable=false,unique=true)
    @Pattern(regexp=".+@.+\\....?")
    @NotNull
    private String email;
    @NotNull
    private String name;

    @Pattern(regexp="\\+?\\d+")
    private String phone;


    @NotNull
    @Temporal(TemporalType.DATE)
    private Date joinedDate;

    private boolean admin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
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


    public void setName(String givenName) {
        this.name = givenName;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public Date getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (email == null) {
            if (other.getEmail() != null)
                return false;
        } else if (!email.equals(other.getEmail()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", passwordHash='" + passwordHash + '\'' +
                ", email='" + email + '\'' +
                ", givenName='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", joinedDate=" + joinedDate +
                ", admin=" + admin +
                '}';
    }
}
