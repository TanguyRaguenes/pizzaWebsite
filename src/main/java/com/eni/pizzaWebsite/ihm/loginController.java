package com.eni.pizzaWebsite.ihm;

import com.eni.pizzaWebsite.bo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class loginController {

    @Autowired
    UserDetailsManager userDetailsManager;

    @GetMapping("customLogin")
    public String getShowLogin(Model model) {
//        model.addAttribute("user", new User());
        return "custom-login";
    }

//    @PostMapping("customLogin")
//    public String postShowLogin() {
//        return "redirect:/user";
//    }

    @GetMapping("/user")
    public String getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "User: " + auth.getName() + ", Roles: " + auth.getAuthorities().toString();
    }


}
