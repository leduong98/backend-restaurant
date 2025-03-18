package com.be.restaurant.domain.service;


import com.be.restaurant.app.dto.LoginRequest;
import com.be.restaurant.app.response.LoginResponse;
import com.be.restaurant.domain.configs.security.TokenProvider;
import com.be.restaurant.domain.entities.CustomUserDetails;
import com.be.restaurant.domain.exceptions.BusinessException;
import com.be.restaurant.domain.repository.RoleRepository;
import com.be.restaurant.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService extends BaseAbstractService {

    private final PasswordEncoder encoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final TokenProvider tokenProvider;

    public Mono<ResponseEntity<LoginResponse>> login(LoginRequest loginRequest, ServerWebExchange exchange) {

        return userRepository.findByEmail(loginRequest.getEmail()).doOnNext(System.out::println)
                .filter(u -> encoder.matches(loginRequest.getPassword(), u.getPassword())).log()
                .flatMap(u -> roleRepository.
                        findById(u.getRoleId())
                        .map(role -> tokenProvider.generateToken(new CustomUserDetails(u, role)))).
                flatMap(token-> {
                    ResponseCookie cookie = ResponseCookie.from("auth_token", token)
                            .httpOnly(true)
                            .secure(false)
                            .path("/")
                            .sameSite("Lax")
                            .maxAge(3600)
                            .build();
                    ServerHttpResponse response = exchange.getResponse();
                    response.addCookie(cookie);
                    return Mono.just(ResponseEntity.ok(new LoginResponse(token)));
                })
                .switchIfEmpty(Mono.error(new BusinessException(HttpStatus.UNAUTHORIZED, "Wrong account")));

    }

}
