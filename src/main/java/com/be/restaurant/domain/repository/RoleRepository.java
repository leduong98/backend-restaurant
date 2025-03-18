package com.be.restaurant.domain.repository;


import com.be.restaurant.domain.entities.Role;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RoleRepository extends ReactiveCrudRepository<Role, Long> {
    Role findByName(String name);

    Mono<Boolean> existsByName(String name);

}
