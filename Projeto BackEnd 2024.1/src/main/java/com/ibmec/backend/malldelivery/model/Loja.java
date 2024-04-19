package com.ibmec.backend.malldelivery.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity(name = "loja")
public class Loja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nome;

    @Column
    private String email;

    @Column
    private String telefone;

    @Column
    private String cnpj;

    @Column
    private LocalDateTime dataCadastro;

    @Column
    private String banner;

    @Column
    private String perfil;

    @Column
    private String urlFacebook;

    @Column
    private String urlInstagram;

    @Column
    private String urlTwitter;

    @Column
    private int numMaxProduto;

    @Column
    private int abaProdAdd;

    @OneToMany
    @JoinColumn(name = "id_loja", referencedColumnName = "id")
    private List<DadoBancario> dadosBancarios;

    @OneToMany
    @JoinColumn(name = "id_loja", referencedColumnName = "id")
    private List<Endereco> enderecos;
}
