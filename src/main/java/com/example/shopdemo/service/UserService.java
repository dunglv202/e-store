package com.example.shopdemo.service;

import com.example.shopdemo.entity.Authority;
import com.example.shopdemo.entity.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.validation.Valid;
import java.util.Set;

public interface UserService {
    User getUser(Integer id);
    User loadUserByUserName(String username) throws UsernameNotFoundException;
    User addUser(@Valid User user);
    User addAuthorities(Integer userId, Set<Authority> authorities);
}
