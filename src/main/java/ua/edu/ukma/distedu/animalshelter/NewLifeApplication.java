package ua.edu.ukma.distedu.animalshelter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories("ua.edu.ukma.distedu.animalshelter.persistence.repository")
@EntityScan("ua.edu.ukma.distedu.animalshelter.persistence.model")
public class NewLifeApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewLifeApplication.class, args);
    }

}
