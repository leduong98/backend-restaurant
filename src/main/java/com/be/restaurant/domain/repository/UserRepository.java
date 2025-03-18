package com.be.restaurant.domain.repository;


import com.be.restaurant.domain.entities.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    Mono<User> findByEmail(String email);

    Mono<User> getById(Long id);
}
