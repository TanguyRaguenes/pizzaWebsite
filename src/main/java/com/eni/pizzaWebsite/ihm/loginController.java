package com.eni.pizzaWebsite.ihm;

import com.eni.pizzaWebsite.bo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {

    @GetMapping("customLogin")
    public String showLogin(Model model) {
        model.addAttribute("client", new User());
        return "customLogin";
    }


}
