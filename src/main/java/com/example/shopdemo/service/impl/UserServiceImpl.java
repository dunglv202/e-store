package com.example.shopdemo.service.impl;

import com.example.shopdemo.entity.Authority;
import com.example.shopdemo.entity.User;
import com.example.shopdemo.exception.AlreadyExistsException;
import com.example.shopdemo.exception.NotFoundException;
import com.example.shopdemo.repository.AuthorityRepository;
import com.example.shopdemo.repository.UserRepository;
import com.example.shopdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@Validated
public class UserServiceImpl implements UserService {
    private UserRepository userRepo;
    private AuthorityRepository authorityRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, AuthorityRepository authorityRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.authorityRepo = authorityRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUser(Integer userId) {
        return userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User not exists - ID: " + userId));
    }

    @Override
    public User loadUserByUserName(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User does not exists: "+ username));
    }

    @Override
    public User addUser(@Valid User user) {
        // check if username existed
        if (userRepo.findByUsername(user.getUsername()).isPresent())
            throw new AlreadyExistsException("Username '" + user.getUsername() +"' already existed");

        // save user to db
        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepo.save(user);

        // add authorities
        return addAuthorities(user.getId(), user.getAuthorities());
    }

    @Override
    public User addAuthorities(Integer userId, Set<Authority> authorities) {
        User foundUser = getUser(userId);

        foundUser.setAuthorities(new HashSet<>());
        if (authorities == null || authorities.size() == 0) {
            foundUser.getAuthorities().add(authorityRepo.findByName("ROLE_CUSTOMER").orElse(null));
        } else {
            authorities.forEach(authority -> {
                Authority foundAuthority = authorityRepo.findByName(authority.getName()).orElseThrow(()->new NotFoundException("Authority not exists -  " +authority.getName()));
                foundUser.getAuthorities().add(foundAuthority);
            });
        }

        return userRepo.save(foundUser);
    }
}
