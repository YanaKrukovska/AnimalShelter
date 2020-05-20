package ua.edu.ukma.distedu.animalshelter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.distedu.animalshelter.persistence.model.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {

}
