package com.napas.achoffline.reportoffline.entity;

import com.napas.achoffline.reportoffline.entity.Role;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TBL_AUTH_USERS",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "username"),
            @UniqueConstraint(columnNames = "email")
        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AUTH_USER_ID")
    @SequenceGenerator(sequenceName = "SEQ_AUTH_USER_ID", allocationSize = 1, name = "SEQ_AUTH_USER_ID")
    private Long id;

    @NotBlank
    //@Size(max = 20)
    @Column(name = "USERNAME")
    private String username;

    @NotBlank
    //@Size(max = 50)
    @Email
    @Column(name = "EMAIL")
    private String email;

   // @NotBlank
   // @Size(max = 120)
    @Column(name = "PASSWORD")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TBL_AUTH_USER_ROLES",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;

    @Column(name = "MODIF_DATE")
    private Timestamp modifDate;

   // @Size(max = 50)
    @Column(name = "FULL_NAME")
    private String fullName;

    public User() {
    }

    public User(String username, String email, String password, String fullName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getModifDate() {
        return modifDate;
    }

    public void setModifDate(Timestamp modifDate) {
        this.modifDate = modifDate;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
