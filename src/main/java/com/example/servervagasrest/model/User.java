package com.example.servervagasrest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role")
public abstract class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{error.required}")
    @Size(min = 3, max = 20, message = "{error.invalid_length}")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{error.invalid_format}")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "{error.required}")
    @Column(nullable = false)
    @Size(min = 4, max = 150,message = "{error.invalid_length}")
    private String name;


    @NotBlank(message = "{error.required}")
    @Size(min = 3, max = 20, message = "{error.invalid_length}")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "{error.invalid_format}")
    @Column(nullable = false)
    private String password;

    @Pattern(regexp = "^$|.{10,14}", message = "{error.invalid_length}")
    private String phone;

    @Email(message = "{error.invalid_format}")
    private String email;

    public void setName(String name) {
        if (name != null) {
            this.name = name.toUpperCase();
        }
    }

    public abstract String getRole();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
