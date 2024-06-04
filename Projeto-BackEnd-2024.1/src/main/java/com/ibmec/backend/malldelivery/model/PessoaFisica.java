package com.ibmec.backend.malldelivery.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "pessoa_fisica")
public class PessoaFisica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome")
    private String nomePessoaFisica;

    @Column(name = "sobrenome")
    private String sobrenomePessoaFisica;

    @Column(name = "email")
    private String emailPessoaFisica;

    @Column(name = "telefone")
    private String telefonePessoaFisica;

    @Column(name = "cpf")
    private String cpfPessoaFisica;

//    @OneToMany
//    @JoinColumn(name = "id_pessoa_fisica", referencedColumnName = "id")
//    private List<Loja> lojas = new ArrayList<>();
}
