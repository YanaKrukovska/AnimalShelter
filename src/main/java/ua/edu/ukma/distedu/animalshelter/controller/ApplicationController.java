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
    private String DEFAULT_IMAGE = "/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAEkAYcDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3+iiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKADIozXF+Ir26g+IXha2iuJEgmFx5sasQr4UYyO9Sx6rfQfE+bSZ5i1lPp6z28eB8rKxDf1/SnYdjr6QmqGranb6NpN1qV022C3jLt747fUniuD0vw1qvjOyXXNd1e/szdDfa2dnL5awofu7v7xxg0kurEemUVwvhPUtR0zX7nwprdwbqeKPz7O7f700WcYb3Gf50/wXd315d+K45rmVzDqkkcJf5vLG1cAA9vanYdjtgc0tcn8PdWu9Y8KxzX8xmu45nhlcgAkq2OcV1dJ6CejFooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigDg/ER834q+FYhyyQXMhHoMAZo19fs3xT8L3C8edDPAT64AP9TWl4h8Fxa7qltqcep31heQxmJZLVgMqTnHIOK4fXvBi23i3w5ZT63q12L15Veaa4O9Nq5+Qj7pOefpVLUpM6f4nObjSdK0lTxqOpQwuPVQdxH6Cu3jjSKNUQBUUAADsBXm/jTRo/DfhzQ57d7ma30rU455HmcyOEYnJJPYEivRoZ4rmBJoZFkikUMrqcgg9CDSeyE9jjNcxH8VPDEijDvBcRtjuu0n+dM+HJ8y48VTjlZNYlwR0OAB/Sklk/tT4wW0cGGTSbB2nPYO/Cj64P6GkX4ZpDPcGy8RazaW88zTNBBMqqGY5POM09B9B3w5H2e48T2Q+5Dq0u0em7nFd5XkHg7wbZ3+v64JtQ1MnTdQCIyXLL5pCg7nx949vpXr9E7X0FLcKKKKkQUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABVO402yuru2u57eOS4tixhdhkoWGDj61cooAr3VrBe2sltcxLLBKpR0cZDA9Qa4Q/D3VdOfy/Dviq9sLM5xbSDzVTP93Jr0Okpp2GnYwfDXha08NWsqwyS3FzcN5lzdTtueVvU+3tW9ilopCKdppllYy3MtrbxxSXUnmzMowXbGMn3q5RRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAZOv6zHoWlvdvG80hYRwwx/elkY4VR6ZPU9gCazLbStfv41udR12S1kcZFtYRKqRZ7FmBZj78fSrHivSrrUrC2ksAj3dlcpcxxs20SbcgrntkE8+tU7e58V6goiisoNKhUYae6IlkJ9FRDt/Enn0oV7jtpdFOa68TaFdsqXcerwLyYZ41il2/7LLwT/vCuj0XWrTXdPF3aO3DFJI3Xa8TjqjL2Yf/AF+hrlJbzU7XX20/VvKedo/Ot7qFCizIpwQVJO1xkHAOKLOf+z/HFpPEAkeqI0FxGvRpEG5G+uAwrihiGqzpT+R1zop0lOPzPQaKBRXacYUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAJTHdY0LOQqgEkk4AA70+vPPEmqSeJNRk0Kyk2adA22+mUn96w6xKfQfxHv09azqVI0480i6dN1JWRBJqA8SeKX1K3JOm2cRt7Z+0zE5dh7cAA98VY09DqPjayjiGY9NRp527K7qURfrgsfyqsrSyXH9jaBbo90gCySEfurVexfH8WOijk+wrtNA0O20LT/ssDPI7uZZ55OXmkPVmPrwOOwAFcNClKrV9vPRdEdtepGnT9lF3NcdKWiivSPPCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigDm/GmsS6P4fc2hxfXTC2tfaRsjd/wEZb8K4+GzltbSx0LSzi9uhgzMN3lqOXlb1PPHqxrY8Z/v/FWg27coiTzYI4LfKoP1AJqfwlCt1r+s6iw+aApYx/7IVQzY+pYf981w1YupXUHslc7qb9lRc1uzodF0az0LT0srKPag+ZmY5aRj1Zj3Ynqa1KTtRXccLd9WLRRmigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigBKCcVmazrdhoNg17fzeXEDtUAZZ2PRVXqWPYVwV9reu+IGIMsul2bH5be3YecR/tv2P8Asr07msa1eFFXmzalQnVf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";

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
    public String submitAnimal(@ModelAttribute Animal animal, @ModelAttribute("date") String test_date, @ModelAttribute("animalPhoto") MultipartFile animalPhoto, Model model) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (test_date.equals("") || animal.getName().equals("") || animal.getAge() == 0) {
            model.addAttribute("animalError", "animalError");
            return "add_animal";
        }

        Date convertedDate = new Date();
        try {
            convertedDate = sdf.parse(test_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String encryptedPhoto = "";
        if (!animalPhoto.isEmpty()) {
            try {
                encryptedPhoto = Base64.encodeBase64String(animalPhoto.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            encryptedPhoto = DEFAULT_IMAGE;

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
        if (currentUser != null) {
            model.addAttribute("notifications", requestService.findAllByUser(userService.findUserByUsername(currentUser.getUsername())).size());
        }
        return "contact";
    }

}
