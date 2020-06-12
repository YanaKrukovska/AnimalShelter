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
    public String addUser(@ModelAttribute User user, Model model) {

        if (user.getUsername().equals("")) {
            model.addAttribute("usernameError", "usernameError");
            return "registration";
        }

        if (user.getEmail().equals("")) {
            model.addAttribute("mailError", "mailError");
            return "registration";
        }

        if (user.getPassword().equals("")) {
            model.addAttribute("passwordError", "passwordError");
            return "registration";
        }

        if (!passwordService.comparePasswordAndConfirmationPassword(user.getPassword(), user.getPasswordConfirm())) {
            model.addAttribute("passwordConfirmError", "passwordConfirmError");
            return "registration";
        }

        if (!userService.saveUser(user)) {
            model.addAttribute("logError", "logError");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login-processing")
    public String loginUser(@ModelAttribute User user, Model model) {

        if (user.getUsername().equals("")) {
            model.addAttribute("usernameError", "usernameError");
            return "login";
        }

        if (userService.findUserByUsername(user.getUsername()) == null) {
            model.addAttribute("userNotFoundError", "userNotFoundError");
            return "login";
        }

        if (user.getUsername().equals("")) {
            model.addAttribute("passwordEmptyError", "passwordEmptyError");
            return "login";
        }

        if (!passwordService.compareRawAndEncodedPassword(user.getPassword(), (userService.findUserByUsername(user.getUsername()).getPassword()))) {
            model.addAttribute("wrongPasswordError", "wrongPasswordError");
            return "login";
        }


        return "redirect:/";
    }

}