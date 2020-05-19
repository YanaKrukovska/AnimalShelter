package ua.edu.ukma.distedu.animalshelter.persistence.repository;

import ua.edu.ukma.distedu.animalshelter.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
