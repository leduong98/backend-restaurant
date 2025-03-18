package com.be.restaurant.app.controller.author;


import com.be.restaurant.app.dto.LoginRequest;
import com.be.restaurant.app.response.LoginResponse;
import com.be.restaurant.domain.service.RoleService;
import com.be.restaurant.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@Slf4j
public class UserController {
    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @GetMapping("/init-role")
    public void initRole(){
        roleService.initRole();
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest, ServerWebExchange serverWebExchange) throws Exception {
        return userService.login(loginRequest, serverWebExchange);
    }

    @PostMapping("/logout")
    public Mono<Void> logout(ServerWebExchange exchange) throws Exception {
        ResponseCookie cookie = ResponseCookie.from("auth_token", "")
                .maxAge(0)
                .path("/")
                .build();
        ServerHttpResponse response = exchange.getResponse();
        response.addCookie(cookie);
        return response.setComplete();
    }

    @GetMapping("/userInfo")
    public Mono<ResponseEntity<User>> getUser() throws Exception {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(item -> ResponseEntity.ok(((org.springframework.security.core.userdetails.User) item)))
                ;
    }

}
