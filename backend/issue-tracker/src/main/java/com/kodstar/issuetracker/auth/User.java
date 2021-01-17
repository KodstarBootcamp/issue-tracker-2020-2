package com.kodstar.issuetracker.auth;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Collection;


@Entity
@Data
@AllArgsConstructor
//@NoArgsConstructor
@Table(name = "t_users")
public class User implements  Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @NotBlank(message = "invalid input, Username can't be null")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "invalid input, password can't be null")
    private String password;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "verification", length = 64)
    private String verification;

    @Column(name = "enabled")
    private boolean enabled;

    public User() {
        this.enabled=false;
    }


/*
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }*/


    public boolean isAccountNonExpired() {
        return false;
    }


    public boolean isAccountNonLocked() {
        return false;
    }

    public boolean isCredentialsNonExpired() {
        return false;
    }
}