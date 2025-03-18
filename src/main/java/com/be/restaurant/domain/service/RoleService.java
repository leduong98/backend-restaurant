package com.be.restaurant.domain.service;

import com.be.restaurant.domain.entities.Role;
import com.be.restaurant.domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository repository;

    public Role findByName(String name) {
        Role role = repository.findByName(name);
        return role;
    }
    public void initRole(){
//        List<Role> roles = new ArrayList();
//        roles.add(Role.builder().name(Roles.USER.toString()).description("").build());
//        roles.add(Role.builder().name(Roles.ADMIN.toString()).description("").build());
//        repository.saveAll(roles);
    }

}
