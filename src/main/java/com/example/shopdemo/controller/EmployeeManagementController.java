package com.example.shopdemo.controller;

import com.example.shopdemo.entity.User;
import com.example.shopdemo.pojo.UserSpecs;
import com.example.shopdemo.repository.AuthorityRepository;
import com.example.shopdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.shopdemo.util.AuthenticationUtils.getUser;

@Controller
@RequestMapping("/manage/employees")
public class EmployeeManagementController {
    private UserService userService;

    @Autowired
    public EmployeeManagementController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String showManagementPage(@RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "20") Integer size,
                                     UserSpecs specs,
                                     Authentication auth,
                                     Model model) {
        User loggedUser = getUser(auth);
        model.addAttribute("specs", specs);
        model.addAttribute("users", userService.getAllUsersManagedBy(loggedUser, specs, PageRequest.of(page-1, size)));
        model.addAttribute("managedRoles", userService.getAllAuthoritiesManagedBy(loggedUser));
        return "emp-management";
    }

    @GetMapping("/add")
    public String addEmployee(Authentication auth,
                              Model model) {
        model.addAttribute("rolesManaged", userService.getAllAuthoritiesManagedBy(getUser(auth)));
        return "register";
    }
}
