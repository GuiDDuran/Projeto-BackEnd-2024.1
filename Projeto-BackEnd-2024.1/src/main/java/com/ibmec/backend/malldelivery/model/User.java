package com.ibmec.backend.malldelivery.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "`user`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String username;

    @Column
    private String password;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Profile> profiles = new ArrayList<>();
}
