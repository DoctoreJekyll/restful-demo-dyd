package org.generations.restfuldemodyd.config;

import org.generations.restfuldemodyd.model.Job;
import org.generations.restfuldemodyd.model.Player;
import org.generations.restfuldemodyd.repository.JobRepository;
import org.generations.restfuldemodyd.repository.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner initData(PlayerRepository playerRepository, JobRepository jobRepository) {
        return args -> {
            playerRepository.save(new Player(null, "Tharion", "Elfo", 5));
            playerRepository.save(new Player(null, "Brug", "Orco", 2));
            playerRepository.save(new Player(null, "Mira", "Humana", 3));
            jobRepository.save(new Job(null, "Warrior", "Melee Class"));
            jobRepository.save(new Job(null, "Wizard", "Range Class"));
        };
    }

}
