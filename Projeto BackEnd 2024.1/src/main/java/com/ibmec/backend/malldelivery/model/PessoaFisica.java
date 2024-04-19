package com.ibmec.backend.malldelivery.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class PessoaFisica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nome;

    @Column
    private String sobrenome;

    @Column
    private String email;

    @Column
    private String telefone;

    @Column
    private String cpf;

    @OneToMany
    @JoinColumn(name = "id_pessoa_fisica", referencedColumnName = "id")
    private List<Loja> lojas;
}
