package com.music.social.services;

import com.music.social.models.AuthGroup;
import com.music.social.models.User;
import com.music.social.principals.UserPrincipal;
import com.music.social.repositories.AuthGroupRepository;
import com.music.social.repositories.UserRepository;
import com.music.social.utils.JwtUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServices implements UserDetailsService{

    @Autowired
    private UserRepository userDao;

    @Autowired
    private AuthGroupRepository authGroupDao;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        //searches the User repository for user by username, object type is UserDetails, the prebuilt Spring Security object to handle User objects,(User is a child class of UserService)
        User user = this.userDao.findByUsername(username);
        //if we cant find our user we throw this
        if(null==user){
            throw new UsernameNotFoundException("cannot find username: " + username);
        }
        //now we check the auth groups 
        List<AuthGroup> authGroups = this.authGroupDao.findByUsername(username);
        return new UserPrincipal(user, authGroups);
    }

    public boolean userIsRole(User user,String role){
        List<AuthGroup> authGroups = this.authGroupDao.findByUsername(user.getUsername());

        return authGroups.stream().anyMatch(group -> 
            group.getAuthGroup().toLowerCase().contains(role));
    }

    public boolean isLoggedIn(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return !(auth instanceof AnonymousAuthenticationToken);
    }

    public User getCurrentUser(HttpServletRequest request){
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);

            UserPrincipal user = (UserPrincipal) this.loadUserByUsername(username);
            //then abstracts the user and gets it from the DB
            return userDao.getOne(user.getUser().getId());
        }
        return null;
        
    }

}