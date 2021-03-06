package com.music.social.principals;



import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.music.social.models.AuthGroup;
import com.music.social.models.User;

public class UserPrincipal implements UserDetails{
    
    /**
     *
     */
    private static final long serialVersionUID = -2935879292557474907L;
    
    private User user;
    private List<AuthGroup> authGroups;

    public UserPrincipal(User user, List<AuthGroup> authGroups){
        super();
        this.user = user;
        this.authGroups = authGroups;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(null == authGroups){
             return Collections.emptySet();
        }
        Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
        authGroups.forEach(group->{
            //grabs each group and puts them in the hashset to return
            grantedAuthorities.add(
                new SimpleGrantedAuthority(group.getAuthGroup())
            );
        });
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
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

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<AuthGroup> getAuthGroups() {
        return this.authGroups;
    }

    public void setAuthGroups(List<AuthGroup> authGroups) {
        this.authGroups = authGroups;
    }

}
