package com.be.restaurant.domain.configs.security;

import com.be.restaurant.domain.entities.CustomUserDetails;
import com.be.restaurant.domain.repository.UserRepository;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;



@Data
@SuperBuilder
public class AuthUser implements ReactiveUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByEmail(username).map(CustomUserDetails::new);
    }
}
