package com.co.ias.reactivetest.reactive.test.repository;

import com.co.ias.reactivetest.reactive.test.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void listUsersTest() {

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

        userRepository.findAll().subscribe(System.out::println);




    }
}
