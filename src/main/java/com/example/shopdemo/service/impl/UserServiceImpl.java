package com.example.shopdemo.service.impl;

import com.example.shopdemo.entity.Authority;
import com.example.shopdemo.entity.User;
import com.example.shopdemo.exception.AlreadyExistsException;
import com.example.shopdemo.exception.NotFoundException;
import com.example.shopdemo.exception.UnauthorizedException;
import com.example.shopdemo.pojo.UserSpecs;
import com.example.shopdemo.repository.AuthorityRepository;
import com.example.shopdemo.repository.UserRepository;
import com.example.shopdemo.service.UserService;
import com.example.shopdemo.spec.UserSpecifications;
import com.example.shopdemo.util.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
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
    public Set<Authority> getAllAuthoritiesManagedBy(User user) {
        Set<Authority> managedAuthorities = new HashSet<>();
        if (user.getAuthorities() == null)
            return null;

        user.getAuthorities().forEach((a) -> managedAuthorities.addAll(authorityRepo.findAllByManagedByContaining(a)));
        return managedAuthorities;
    }

    @Override
    public Set<User> getAllUsersManagedBy(User loggedUser, UserSpecs specs, Pageable pagination) {
        Specification<User> specification = Specification.where(null);
        Set<Authority> managedAuthorities = getAllAuthoritiesManagedBy(loggedUser);

        Set<Authority> specifiedAuthorities = new HashSet<>();
        // if no authority was specified by specs, just select all managed authorities
        if (specs.getAuthorities() == null) {
            specifiedAuthorities.addAll(managedAuthorities);
        } else {
            for (Authority authority : managedAuthorities) {
                if (specs.getAuthorities().contains(authority.getName()))
                    specifiedAuthorities.add(authority);
            }
            // if no specified authority is legally managed by logged user, return null
            if (specifiedAuthorities.size() == 0)
                return null;
        }

        // make specification for specified authorities
        for (Authority authority : specifiedAuthorities) {
            specification = specification.or(UserSpecifications.hasAuthority(authority));
        }

        // add constraint of matching keyword
        specification = specification.and(UserSpecifications.matchesKeyword(specs.getKeyword()));
        return userRepo.findAll(specification, pagination).toSet();
    }

    @Override
    public User addUser(@Valid User user, Authentication auth) {
        // check if username existed
        if (userRepo.findByUsername(user.getUsername()).isPresent())
            throw new AlreadyExistsException("Username '" + user.getUsername() +"' already existed");

        // save user to db
        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepo.save(user);

        // add authorities to user
        if (auth == null || !auth.isAuthenticated()) {
            return addAuthorities(user.getId(), null, null);
        } else {
            return addAuthorities(user.getId(), user.getAuthorities(), AuthenticationUtils.getUser(auth));
        }
    }

    @Override
    public User addAuthority(Integer userId, Integer authorityId, User loggedUser) {
        User foundUser = getUser(userId);
        Authority authority = authorityRepo.findById(authorityId).orElseThrow(() -> new NotFoundException("Authority not found - ID: " + authorityId));

        // check for valid authority
        Authority foundAuthority = authorityRepo.findByName(authority.getName()).orElseThrow(()->new NotFoundException("Authority not exists -  " +authority.getName()));

        // check if authority is managed by currently logged user
        if (loggedUser.getAuthorities().stream().anyMatch((a) -> foundAuthority.getManagedBy().contains(a))) {
            foundUser.getAuthorities().add(foundAuthority);
        } else {
            throw new UnauthorizedException("You are not supposed to manage users with authority '"+ foundAuthority.getName() + "'");
        }

        return userRepo.save(foundUser);
    }

    /**
     * add authority of ROLE_CUSTOMER is argument 'authorities' is null,
     * or else add all authorities with right of loggedUser
     * */
    private User addAuthorities(Integer userId, Set<Authority> authorities, User loggedUser) {
        if (authorities == null) {
            final Authority ROLE_CUSTOMER = authorityRepo.findByName("ROLE_CUSTOMER").orElseThrow(() -> new NotFoundException("ROLE_CUSTOMER not found"));
            getUser(userId).getAuthorities().add(ROLE_CUSTOMER);
        } else {
            authorities.forEach(authority -> {
                addAuthority(userId, authority.getId(), loggedUser);
            });
        }

        return getUser(userId);
    }

    @Override
    public User removeAuthority(Integer userId, Integer authorityId, User loggedUser) {
        // check if authority is managed by logged user
        Authority foundAuthority = authorityRepo.findById(authorityId).orElseThrow(() -> new NotFoundException("Authority not found: " + authorityId));
        Set<Authority> managedAuthorities = getAllAuthoritiesManagedBy(loggedUser);
        if (!managedAuthorities.contains(foundAuthority))
            throw new UnauthorizedException("You are not supposed to manage " + foundAuthority.getName());

        // get user from db
        User foundUser = getUser(userId);

        // remove authority from user and save
        foundUser.getAuthorities().remove(foundAuthority);
        return userRepo.save(foundUser);
    }

    @Override
    public User deleteUser(Integer userId, Authentication auth) {
        User user = getUser(userId);

        // check if logged user is the owner
        if (!AuthenticationUtils.getUser(auth).getId().equals(userId))
            throw new UnauthorizedException("You are not the owner of this account");

        user.setEnabled(false);
        return user;
    }
}
