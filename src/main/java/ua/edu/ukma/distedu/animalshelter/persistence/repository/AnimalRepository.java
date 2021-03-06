package ua.edu.ukma.distedu.animalshelter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.edu.ukma.distedu.animalshelter.persistence.model.Animal;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    Animal findAnimalById(long id);
    List<Animal> findAllByAdoptionDateNull();
}
