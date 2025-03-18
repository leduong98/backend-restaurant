package com.be.restaurant.domain.configs;


import com.be.restaurant.domain.entities.Role;
import com.be.restaurant.domain.entities.User;
import com.be.restaurant.domain.entities.enums.Roles;
import com.be.restaurant.domain.repository.RoleRepository;
import com.be.restaurant.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class StartRunner implements ApplicationRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public void run(ApplicationArguments args) throws IOException {
        roleRepository.existsByName("Admin")
                .filter(Boolean.FALSE::equals)
                .flatMap(__ -> roleRepository.save( // Lưu role "Admin"
                        Role.builder()
                                .name(Roles.ADMIN.val)
                                .description("")
                                .build()
                ))
                .flatMap(savedRole -> userRepository.save( // Lưu user "Admin"
                        User.builder()
                                .email("admin@gmail.com")
                                .username("admin@gmail.com")
                                .name("Admin")
                                .roleId(savedRole.getId())
                                .password(passwordEncoder.encode("123"))
                                .build()
                ))
                .subscribe();
    }

}
