package ua.edu.ukma.distedu.animalshelter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.edu.ukma.distedu.animalshelter.persistence.model.Request;
import ua.edu.ukma.distedu.animalshelter.persistence.model.User;
import ua.edu.ukma.distedu.animalshelter.service.AnimalService;
import ua.edu.ukma.distedu.animalshelter.service.RequestService;
import ua.edu.ukma.distedu.animalshelter.service.UserService;

import java.util.Date;

@Controller
public class ApplicationController {
    private final UserService userService;
    private final AnimalService animalService;
    private final RequestService requestService;

    @Autowired
    public ApplicationController(UserService userService, AnimalService animalService, RequestService requestService) {
        this.userService = userService;
        this.animalService = animalService;
        this.requestService = requestService;
    }

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("animals", animalService.getAllAnimals());
        model.addAttribute("requests", requestService.getAllRequests());
        return "index";
    }

    @PostMapping("/sendRequest")
    public String sendRequest(Model model, @RequestParam Long animalId, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findUserByUsername(currentUser.getUsername());
        model.addAttribute("currentStudent", user);
        model.addAttribute("animals", animalService.getAllAnimals());
        model.addAttribute("requests", requestService.getAllRequests());
        requestService.addRequest(new Request(user, animalService.findAnimalById(animalId), new Date()));
        return "redirect:/";
    }

    @GetMapping("/users")
    public String openUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

}
