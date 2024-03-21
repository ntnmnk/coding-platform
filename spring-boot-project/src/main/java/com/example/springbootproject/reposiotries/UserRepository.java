package com.example.springbootproject.reposiotries;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.springbootproject.entities.User;
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findAllByOrderByScoreDesc();

    Optional<User> findByUsername(String username);




    

}
