package ua.edu.ukma.distedu.animalshelter.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ukma.distedu.animalshelter.persistence.model.Animal;
import ua.edu.ukma.distedu.animalshelter.persistence.repository.AnimalRepository;
import ua.edu.ukma.distedu.animalshelter.persistence.repository.UserRepository;
import ua.edu.ukma.distedu.animalshelter.service.AnimalService;

import java.util.Date;
import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;

    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    @Override
    public List<Animal> findAllByAdoptionDateNull() {
        return animalRepository.findAllByAdoptionDateNull();
    }

    @Override
    public Animal addAnimal(Animal newAnimal) {
        return animalRepository.save(newAnimal);
    }

    @Override
   public Animal findAnimalById(long id){
        return animalRepository.findAnimalById(id);
    }

    @Override
    public void updateAnimal(Animal animal, Date adoptionDate) {
        Animal animalDB = animalRepository.findAnimalById(animal.getId());
        animalDB.setAdoptionDate(adoptionDate);
        animalRepository.save(animalDB);
    }

}
