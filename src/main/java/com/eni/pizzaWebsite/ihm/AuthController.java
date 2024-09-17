package com.eni.pizzaWebsite.ihm;

import com.eni.pizzaWebsite.bll.IUserManager;
import com.eni.pizzaWebsite.bo.User;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    IUserManager userManager;

    public AuthController(IUserManager userManager) {
        this.userManager = userManager;
    }

    @GetMapping("/")
    public String home() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User: " + auth.getName() + ", Roles: " + auth.getAuthorities().toString());
        return "index";
    }

    @GetMapping("customLogin")
    public String getShowLogin(Model model) {
//        model.addAttribute("user", new User());
        return "custom-login";
    }

    @GetMapping("authForm")
    public String getAuthForm(Model model) {

        User user =new User();
        model.addAttribute("user", user);
        return "auth-form";

    }

    @PostMapping("authForm")
    public String postAuthForm(@Valid @ModelAttribute User user, BindingResult bindingResult) {

        System.out.println("User reçu de authForm " + user.toString());

        if(bindingResult.hasErrors()) {
            System.out.println("Erreur de contrôle surface");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userRole = auth.getAuthorities().toString();
        userManager.addUserToList(user,userRole);

        return "redirect:/authForm";

    }


}
