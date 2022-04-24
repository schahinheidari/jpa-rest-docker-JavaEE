package com.example.tp.entities;

import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.UniqueConstraint;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User {

    @Id
    @NotBlank(message = "Email is mandatory")
    @Column(length = 30)
    @JsonProperty("Email")
    private String email;

    @NotBlank(message = "Name is mandatory")
    @Column(length = 50)
    @JsonProperty("Name")
    private String firstName;

    @NotBlank(message = "Family is mandatory")
    @Column(length = 50)
    @JsonProperty("Family")
    private String lastName;

    @NotBlank(message = "Password is mandatory")
    @Column(length = 100)
    @JsonProperty("Password")
    private String password;

    @Column(length = 250)
    @JsonProperty("Image")
    private String image;

//	@Column(length = 10)
//	@JsonProperty("Role")
//    private String role;


    @JsonProperty("Roles")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_email", referencedColumnName = "email"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;


    @JsonProperty("Active")
    private boolean isActive = true;

    public User() {
        super();
    }


    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }


    public User(String email, String firstName, String lastName, String password, String image, Collection<Role> roles, boolean isActive) {
        super();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.image = image;
        this.roles = roles;
        this.isActive = isActive;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }


    @Override
    public String toString() {
        return "User [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", password=" + password
                + ", image=" + image + ", roles=" + roles + ", isActive=" + isActive + "]";
    }


}
