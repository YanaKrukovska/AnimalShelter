package ua.edu.ukma.distedu.animalshelter.service;

import ua.edu.ukma.distedu.animalshelter.persistence.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User createUser(User user);
}

