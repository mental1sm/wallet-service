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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5, message = "Не меньше 5 знаков")
    @NotNull
    private String username;

    private String firstName;
    private String lastName;
    private String email;
    @ColumnDefault("false")
    private boolean emailVerified;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "accounts")
    private Set<Role> roles;

}
