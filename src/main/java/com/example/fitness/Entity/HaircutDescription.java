package com.example.fitness.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@RequiredArgsConstructor
@Entity
@Table(name="HaircutDescription")
@Getter
@Setter
public class HaircutDescription {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private int term;
    @Column
    private int amountVisit;

    @Column
    private int price;
}
