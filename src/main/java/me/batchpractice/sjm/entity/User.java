package me.batchpractice.sjm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "USERS")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String password;
    private String email;
    private String authority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton((GrantedAuthority) this::getAuthority);
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    //기본은 false 인데 true 로 바꿔야 함.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //기본은 false 인데 true 로 바꿔야 함.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //기본은 false 인데 true 로 바꿔야 함.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //기본은 false 인데 true 로 바꿔야 함.
    @Override
    public boolean isEnabled() {
        return true;
    }
}
