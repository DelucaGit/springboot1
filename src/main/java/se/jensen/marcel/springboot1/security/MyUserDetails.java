package se.jensen.marcel.springboot1.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import se.jensen.marcel.springboot1.model.User;

import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private final User user;

    public MyUserDetails(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // VIKTIGT: Returnera det hashade lösenordet från DB
    }

    @Override
    public String getUsername() {
        return user.getUsername(); // VIKTIGT: Returnera användarnamnet
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Ändrat till true så kontot fungerar
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Ändrat till true
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Ändrat till true
    }

    @Override
    public boolean isEnabled() {
        return true; // Ändrat till true
    }
}