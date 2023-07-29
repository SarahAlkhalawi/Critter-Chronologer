package com.udacity.jdnd.course3.critter.entity;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Nationalized
    private String name;
    private String phoneNumber;
    private String notes;
    @OneToMany(targetEntity = Pet.class, mappedBy = "customer")
    private List<Pet> pets;
}
