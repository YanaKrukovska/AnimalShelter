package ua.edu.ukma.distedu.animalshelter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.edu.ukma.distedu.animalshelter.persistence.model.User;
import ua.edu.ukma.distedu.animalshelter.service.impl.PasswordServiceImpl;
import ua.edu.ukma.distedu.animalshelter.service.impl.UserServiceImpl;

@Controller
public class RegistrationController {

    private final UserServiceImpl userService;
    private final PasswordServiceImpl passwordService;

    @Autowired
    public RegistrationController(UserServiceImpl userService, PasswordServiceImpl passwordService) {
        this.userService = userService;
        this.passwordService = passwordService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute User user) {

        if (!passwordService.comparePasswordAndConfirmationPassword(user.getPassword(), user.getPasswordConfirm())) {
            return "redirect:/registration";
        }

        if (!userService.saveUser(user)) {
            return "redirect:/registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user) {

        if (userService.findUserByUsername(user.getUsername()) == null) {
            return "redirect:/login";
        }
        if (!userService.findUserByUsername(user.getUsername()).getPassword().equals(user.getPassword())) {
            return "redirect:/login";
        }
        return "redirect:/";
    }


}