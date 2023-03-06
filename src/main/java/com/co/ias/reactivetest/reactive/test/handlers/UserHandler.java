package com.co.ias.reactivetest.reactive.test.handlers;

import com.co.ias.reactivetest.reactive.test.model.User;
import com.co.ias.reactivetest.reactive.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    @Autowired
    private UserRepository userRepository;

    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Mono<ServerResponse> listUsers(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userRepository.findAll(), User.class
                );
    }

    public Mono<ServerResponse> getUser(ServerRequest request) {

        int userId = Integer.valueOf(request.pathVariable("id"));

        return userRepository.findById(userId)
                .flatMap(user -> ServerResponse
                        .ok()
                        .bodyValue(user))
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue("NO SE ENCONTRO EL USUARIO CON ID: " + userId))
                .onErrorResume(error -> ServerResponse
                        .badRequest()
                        .bodyValue("ERROR AL LEER EL USUARIO: " + error.getMessage()));
    }


    public Mono<ServerResponse> createUser (ServerRequest request) {
        return request
                .bodyToMono(User.class)
                .flatMap(userRepository::save)
                .flatMap(savedUser -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .bodyValue(savedUser))
                .onErrorResume(error -> ServerResponse
                        .status(HttpStatus.BAD_REQUEST)
                        .bodyValue("ERROR AL CREAR EL USUARIO: " + error.getMessage()));
    }


}
