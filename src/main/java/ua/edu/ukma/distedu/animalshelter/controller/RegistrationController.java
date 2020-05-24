package ua.edu.ukma.distedu.animalshelter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.edu.ukma.distedu.animalshelter.persistence.model.User;
import ua.edu.ukma.distedu.animalshelter.service.impl.UserServiceImpl;

@Controller
public class RegistrationController {

    private final UserServiceImpl userService;

    @Autowired
    public RegistrationController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute User user) {

/*        if (bindingResult.hasErrors()) {
            return "registration";
        }*/
      /*  if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }*/

        if (!userService.saveUser(user)) {
            // model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
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
            return "login";
        }
        if (!userService.findUserByUsername(user.getUsername()).getPassword().equals(user.getPassword())) {
            return "login";
        }
        return "redirect:/";
    }


}