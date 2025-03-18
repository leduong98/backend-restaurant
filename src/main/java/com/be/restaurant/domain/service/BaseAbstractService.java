package com.be.restaurant.domain.service;


import com.be.restaurant.domain.entities.User;
import com.be.restaurant.domain.exceptions.BusinessException;
import com.be.restaurant.domain.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class BaseAbstractService {
    @Autowired
    protected UserRepository userRepository;


    @Autowired
    protected ModelMapper modelMapper;

    public Mono<User> getUserByEmail(String email) throws Exception {
        return userRepository.findByEmail(email).switchIfEmpty(Mono.error( new BusinessException(HttpStatus.NOT_FOUND, "User not exist")));
    }
}
