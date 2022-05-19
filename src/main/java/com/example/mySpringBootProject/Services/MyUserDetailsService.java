package com.example.mySpringBootProject.Services;

import com.example.mySpringBootProject.Model.Users;
import com.example.mySpringBootProject.Repository.RolesRepository;
import com.example.mySpringBootProject.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RolesRepository rolesRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.getUserByUsername(username);
        if (users == null) {
            throw new UsernameNotFoundException("Could not find user");
        } else {
            List<GrantedAuthority> roles = new ArrayList<>();
            Long userRole = users.getRoleId();
            String roleName = rolesRepository.getRoleName(userRole);

            roles.add(new SimpleGrantedAuthority("ROLE_" + roleName));
            return new MyUserDetails(users, roles);
        }
    }
}
