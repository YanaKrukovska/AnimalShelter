package ua.edu.ukma.distedu.animalshelter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.distedu.animalshelter.persistence.model.Animal;
import ua.edu.ukma.distedu.animalshelter.persistence.model.Request;
import ua.edu.ukma.distedu.animalshelter.persistence.model.User;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByUser(User user);

    List<Request> findAllByAnimal(Animal animal);

    Request findById(long id);

}
