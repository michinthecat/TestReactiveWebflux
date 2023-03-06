package com.co.ias.reactivetest.reactive.test.handlers;

import com.co.ias.reactivetest.reactive.test.components.UserRouter;
import com.co.ias.reactivetest.reactive.test.model.User;
import com.co.ias.reactivetest.reactive.test.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebFluxTest
@ContextConfiguration(classes = {UserRouter.class, UserHandler.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserHandlerTest {

    @Autowired
    private ApplicationContext applicationContext;

    @MockBean
    private UserRepository userRepository;

    private WebTestClient webTestClient;

    @BeforeAll
    void beforeAll() {
        webTestClient = WebTestClient.bindToApplicationContext(applicationContext).build();
    }

    @Test
    void listUsers() {
        User user1 = User.builder()
                .id(1)
                .name("user1")
                .email("user1@user1.com")
                .build();
        User user2 = User.builder()
                .id(2)
                .name("user2")
                .email("user2@user2.com")
                .build();


        when (userRepository.findAll()).thenReturn(Flux.just(user1, user2));

        webTestClient.get()
                .uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class)
                .value(users -> assertThat(users.get(0).getName()).isEqualTo("user1"))
                .value(users -> assertThat(users.get(1).getName()).isEqualTo("user2"))
                .hasSize(2)
                ;

    }

    @Test
    void getUser() {
        User user1 = User.builder()
                .id(1)
                .name("user1")
                .email("user1@user1.com")
                .build();

        when (userRepository.findById(1)).thenReturn(Mono.just(user1));

        webTestClient.get()
                .uri("/users/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(user -> assertThat(user.getName()).isEqualTo("user1"))
                .value(user -> assertThat(user.getEmail()).isEqualTo("user1@user1.com"));

    }

    @Test
    void createUser() {
        User user1 = User.builder()
                .id(1)
                .name("user1")
                .email("user1@user1.com")
                .build();

        when (userRepository.save(user1)).thenReturn(Mono.just(user1));

        webTestClient.post()
                .uri("/users")
                .bodyValue(user1)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(User.class)
                .value(user -> assertThat(user.getName()).isEqualTo("user1"))
                .value(user -> assertThat(user.getEmail()).isEqualTo("user1@user1.com"));

    }

    @Test
    void errorCreateUser () {
        User user1 = User.builder().build();

        webTestClient.post()
                .uri("/users")
                .bodyValue(user1)
                .exchange()
                .expectStatus().isBadRequest();

    }
    @Test
    void userNotFound() {
        when (userRepository.findById(1)).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/users/1")
                .exchange()
                .expectStatus().isNotFound();
    }
}