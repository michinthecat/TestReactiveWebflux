package com.co.ias.reactivetest.reactive.test.components;

import com.co.ias.reactivetest.reactive.test.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> route(UserHandler handler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/users").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::listUsers)
                .andRoute(RequestPredicates.GET("/users/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::getUser)
                .andRoute(RequestPredicates.POST("/users").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), handler::createUser);
    }
}
