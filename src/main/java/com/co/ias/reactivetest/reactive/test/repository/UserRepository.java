package com.co.ias.reactivetest.reactive.test.repository;

import com.co.ias.reactivetest.reactive.test.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {
}
