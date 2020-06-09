package ua.edu.ukma.distedu.animalshelter.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.edu.ukma.distedu.animalshelter.persistence.model.Animal;
import ua.edu.ukma.distedu.animalshelter.persistence.model.Request;
import ua.edu.ukma.distedu.animalshelter.persistence.model.User;
import ua.edu.ukma.distedu.animalshelter.service.AnimalService;
import ua.edu.ukma.distedu.animalshelter.service.RequestService;
import ua.edu.ukma.distedu.animalshelter.service.UserService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public String main(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        model.addAttribute("animals", animalService.findAllByAdoptionDateNull());
        model.addAttribute("requests", requestService.getAllRequests());

        if (currentUser != null) {
            model.addAttribute("notifications", requestService.findAllByUser(userService.findUserByUsername(currentUser.getUsername())).size());
        }
        return "index";
    }


    @GetMapping("/users")
    public String openUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }


    @GetMapping("/my-requests")
    public String redirectToNotifications(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findUserByUsername(currentUser.getUsername());
        model.addAttribute("currentStudent", user);
        model.addAttribute("requests", requestService.findAllByUser(user));
        model.addAttribute("notifications", requestService.findAllByUser(userService.findUserByUsername(currentUser.getUsername())).size());
        return "my_requests";
    }


    @PostMapping("/sendRequest")
    public String sendRequest(Model model, @RequestParam Long animalId, @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findUserByUsername(currentUser.getUsername());
        model.addAttribute("currentStudent", user);
        model.addAttribute("animals", animalService.getAllAnimals());
        model.addAttribute("requests", requestService.getAllRequests());
        requestService.addRequest(new Request(user, animalService.findAnimalById(animalId), new Date(), "pending"));
        return "redirect:/";
    }

    @GetMapping("/add-animal")
    public String addAnimal(Model model) {
        model.addAttribute("animal", new Animal());
        model.addAttribute("date", "");
        model.addAttribute("animalPhoto", null);
        return "add_animal";
    }

    @PostMapping("/add-animal")
    public String submitAnimal(@ModelAttribute Animal animal, @ModelAttribute("date") String test_date, @ModelAttribute("animalPhoto") MultipartFile animalPhoto) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date convertedDate = new Date();
        try {
            convertedDate = sdf.parse(test_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String encryptedPhoto = "";

        if (animalPhoto != null) {
            try {
                encryptedPhoto = Base64.encodeBase64String(animalPhoto.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        animalService.addAnimal(new Animal(animal.getName(), animal.getBreed(), animal.getGender(), animal.getAge(), convertedDate, null, encryptedPhoto));
        return "redirect:/";
    }

    @GetMapping("/requests")
    public String showRequestsList(Model model) {
        model.addAttribute("requests", requestService.getAllRequests());
        model.addAttribute("requestId", -1);
        model.addAttribute("accepted", false);
        return "requests";
    }

    @PostMapping("/acceptRequest")
    public String acceptRequest(@ModelAttribute("requestId") long requestId) {
        Request request = requestService.findById(requestId);
        requestService.updateRequests(requestService.findAllByAnimal(request.getAnimal()), "rejected");
        requestService.updateRequest(request, "accepted");
        animalService.updateAnimal(animalService.findAnimalById(request.getAnimal().getId()), new Date());
        return "redirect:/requests";
    }

    @PostMapping("/rejectRequest")
    public String rejectRequest(@ModelAttribute("requestId") long requestId) {
        requestService.updateRequest(requestService.findById(requestId), "rejected");
        return "redirect:/requests";
    }

    @GetMapping("/contacts")
    public String openContact(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        model.addAttribute("notifications", requestService.findAllByUser(userService.findUserByUsername(currentUser.getUsername())).size());
        return "contact";
    }

}
