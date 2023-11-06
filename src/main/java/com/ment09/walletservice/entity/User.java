package com.ment09.walletservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "t_user", schema = "accounts")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, message = "Не меньше 5 знаков")
    @NotNull
    private String username;

    @NotNull
    @Size(min = 5, message = "Не меньше 5 знаков")
    private String password;

    private String firstName;
    private String lastName;
    private String email;
    @ColumnDefault("false")
    private boolean emailVerified;
    @ColumnDefault("true")
    private boolean nonExpired;
    @ColumnDefault("true")
    private boolean nonLocked;
    @ColumnDefault("true")
    private boolean credentialsNonExpired;
    @ColumnDefault("true")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "accounts")
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return nonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return nonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
