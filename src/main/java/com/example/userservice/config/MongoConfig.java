package com.example.userservice.config;


import com.example.userservice.repository.BookingRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = BookingRepository.class)
@Configuration
public class MongoConfig {
//    @Bean
//    CommandLineRunner commandLineRunner(UserRepository userRepository) {
//        return strings -> {
//            userRepository.save(new User(1, "Calvin", "Ryan"));
//            userRepository.save(new User(2, "Bryson", "Reid"));
//        };
//    }
}