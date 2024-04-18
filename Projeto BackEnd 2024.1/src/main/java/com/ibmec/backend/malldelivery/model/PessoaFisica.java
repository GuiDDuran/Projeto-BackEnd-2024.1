package com.ibmec.backend.malldelivery.model;

import lombok.Data;

@Data
public class PessoaFisica {
    private int idPessoaFisica;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private String cpf;
}
