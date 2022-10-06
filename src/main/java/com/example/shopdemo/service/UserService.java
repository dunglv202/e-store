package com.example.shopdemo.service;

import com.example.shopdemo.entity.Authority;
import com.example.shopdemo.entity.User;
import com.example.shopdemo.pojo.UserSpecs;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.validation.Valid;
import java.util.Set;

public interface UserService {
    User getUser(Integer id);
    User loadUserByUserName(String username) throws UsernameNotFoundException;
    Set<Authority> getAllAuthoritiesManagedBy(User user);
    Set<User> getAllUsersManagedBy(User loggedUser, UserSpecs specs, Pageable pagination);

    User addUser(@Valid User user, Authentication auth);

    User addAuthority(Integer userId, Integer authorityId, User loggedUser);
    User removeAuthority(Integer userId, Integer authorityId, User loggedUser);

    User deleteUser(Integer userId, Authentication auth);
}
