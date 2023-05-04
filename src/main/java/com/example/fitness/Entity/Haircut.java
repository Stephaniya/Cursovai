package com.example.fitness.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@RequiredArgsConstructor
@Entity
@Table(name="Haircut")
@Getter
@Setter
public class Haircut {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private String name;
    @Column String email;

    @Column String phone;

    @Column String date;

    @Column String type;

    @Column String price;
}
