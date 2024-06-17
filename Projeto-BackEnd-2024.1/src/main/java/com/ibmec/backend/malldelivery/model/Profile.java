package com.ibmec.backend.malldelivery.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;
}
