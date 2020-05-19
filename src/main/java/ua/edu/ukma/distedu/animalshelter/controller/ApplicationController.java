package ua.edu.ukma.distedu.animalshelter.controller;

import ua.edu.ukma.distedu.animalshelter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ApplicationController {
    private final UserService userService;

    @Autowired
    public ApplicationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String main(Model model) {
    model.addAttribute("users", userService.getAllUsers());
          return "index";
    }

    @GetMapping("/users")
    public String openUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

}
