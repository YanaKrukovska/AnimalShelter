package ua.edu.ukma.distedu.animalshelter.service;

import ua.edu.ukma.distedu.animalshelter.persistence.model.Animal;

import java.util.List;

public interface AnimalService {
    List<Animal> getAllAnimals();
    Animal addAnimal(Animal animal);
    Animal findAnimalById(long id);

}

