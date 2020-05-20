package ua.edu.ukma.distedu.animalshelter.service;

import ua.edu.ukma.distedu.animalshelter.persistence.model.Request;

import java.util.List;

public interface RequestService {
    List<Request> getAllRequests();

    Request addRequest(Request request);
}

