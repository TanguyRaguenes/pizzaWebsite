package com.eni.pizzaWebsite.ihm;

import com.eni.pizzaWebsite.bll.IClientManager;
import com.eni.pizzaWebsite.bo.Client;
import com.eni.pizzaWebsite.dao.IDAOClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ClientController {

    @Autowired
    private IClientManager clientManager;

    @Autowired
    private IDAOClient daoClient;

    @GetMapping("/client-form")
    public String showClientForm(Model model) {
        model.addAttribute("client", new Client());
        return "client-form";
    }


    @PostMapping("/client-form")
    public String submitClientForm(@ModelAttribute("client") Client client) {
        clientManager.addClientToList(client);
        return "redirect:/clients-list";
    }

    @GetMapping("/clients-list")
    public String showClientsList(Model model) {
        List<Client> clients = clientManager.getClientsList();
        model.addAttribute("clients", clients);
        return "clients-list";
    }
}

