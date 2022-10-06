package com.example.shopdemo.rest;

import com.example.shopdemo.entity.Authority;
import com.example.shopdemo.entity.User;
import com.example.shopdemo.pojo.UserSpecs;
import com.example.shopdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.example.shopdemo.util.AuthenticationUtils.getUser;

@RestController
@RequestMapping("/api/v1/users")
public class UserRestController {
    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public Set<User> getUsersManagedByLoggedUser(@RequestParam Integer page,
                                                 @RequestParam Integer size,
                                                 UserSpecs specs,
                                                 Authentication auth) {
        return userService.getAllUsersManagedBy(getUser(auth), specs, PageRequest.of(page, size));
    }

    @PostMapping("")
    public User register(@RequestBody User user, Authentication auth) {
        return userService.addUser(user, auth);
    }

    @DeleteMapping("/{userId}")
    public User deleteAccount(@PathVariable Integer userId,
                              Authentication auth) {
        return userService.deleteUser(userId, auth);
    }

    @PostMapping("/{userId}/authorities")
    public User addAuthority(@PathVariable Integer userId,
                             @RequestBody Authority authority,
                             Authentication auth) {
        return userService.addAuthority(userId, authority.getId(), getUser(auth));
    }

    @DeleteMapping("/{userId}/authorities/{authorityId}")
    public User removeAuthority(@PathVariable Integer userId,
                                @PathVariable Integer authorityId,
                                Authentication auth) {
        return userService.removeAuthority(userId, authorityId, getUser(auth));
    }
}
