package com.HarmonyTracker.Entities;


import com.HarmonyTracker.Entities.Enums.AuthType;
import com.HarmonyTracker.Entities.Enums.GenderType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String oauthId;
    @Enumerated(EnumType.STRING)
    private AuthType authType;
    private String firstname;
    private String lastname;
    private String email;
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;
    private String password;
    @Enumerated(EnumType.STRING)
    private GenderType genderType;
    private Long birthDate;
    private double weight;
    private double height;
    private boolean extraData;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private UserExtraDetails userExtraDetails;
    private boolean initialized=false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
