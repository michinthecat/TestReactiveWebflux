package com.co.ias.reactivetest.reactive.test.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@ToString
@Getter
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private int id;
    private String name;
    private String email;
}
