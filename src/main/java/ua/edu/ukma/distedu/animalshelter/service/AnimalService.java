package ua.edu.ukma.distedu.animalshelter.service;

import ua.edu.ukma.distedu.animalshelter.persistence.model.Animal;

import java.util.Date;
import java.util.List;

public interface AnimalService {
    List<Animal> getAllAnimals();
    List<Animal> findAllByAdoptionDateNull();
    Animal addAnimal(Animal animal);
    Animal findAnimalById(long id);
    void updateAnimal(Animal animal, Date adoptionDate);

}

