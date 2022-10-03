package com.example.shopdemo.controller;

import com.example.shopdemo.pojo.ProductSpecs;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("")
    public String showHomepage(ProductSpecs specs, Model model) {
        model.addAttribute("specs", specs);
        return "index";
    }

    @GetMapping("/nav")
    public String showNavigationMenu() {
        return "fragments/navigation";
    }
}
