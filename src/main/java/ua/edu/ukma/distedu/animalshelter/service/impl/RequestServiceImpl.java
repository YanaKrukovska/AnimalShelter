package ua.edu.ukma.distedu.animalshelter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ukma.distedu.animalshelter.persistence.model.Request;
import ua.edu.ukma.distedu.animalshelter.persistence.model.User;
import ua.edu.ukma.distedu.animalshelter.persistence.repository.RequestRepository;
import ua.edu.ukma.distedu.animalshelter.service.RequestService;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    @Autowired
    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    @Override
    public List<Request> findAllByUser(User user) {
        return requestRepository.findAllByUser(user);
    }

    @Override
    public Request findById(long id) {
        return requestRepository.findById(id);
    }

    @Override
    public Request addRequest(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public void updateRequest(Request request, String status) {
        Request requestDB = requestRepository.findById(request.getId());
        request.setStatus(status);
        requestRepository.save(requestDB);
    }
}
