package ua.edu.ukma.distedu.animalshelter.service;

import ua.edu.ukma.distedu.animalshelter.persistence.model.Animal;
import ua.edu.ukma.distedu.animalshelter.persistence.model.Request;
import ua.edu.ukma.distedu.animalshelter.persistence.model.User;

import java.util.List;

public interface RequestService {
    List<Request> getAllRequests();

    List<Request> findAllByUser(User user);

    List<Request> findAllByAnimal(Animal animal);

    Request findById(long id);

    Request addRequest(Request request);

    void updateRequest(Request request, String status);

    void updateRequests(List<Request> requests, String status);
}

