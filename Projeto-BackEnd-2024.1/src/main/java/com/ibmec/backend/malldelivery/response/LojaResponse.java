package com.ibmec.backend.malldelivery.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LojaResponse {
    private int id;
    private String nomeLoja;
    private String emailLoja;
    private String telefoneLoja;
    private String cnpj;
    private String bannerLoja;
    private String perfilLoja;
    private String urlFacebook;
    private String urlInstagram;
    private String urlTwitter;
    private int numMaxProduto = 10;
    private int abaProdAdd = 3;
    private String nomeBanco;
    private String agencia;
    private String conta;
    private String codigoBanco;
    private String tipoConta;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String cep;
    private String pais;
    private String estado;
    private String complemento;
    private String descricao;
    private String nomePessoaFisica;
    private String sobrenomePessoaFisica;
    private String emailPessoaFisica;
    private String telefonePessoaFisica;
    private String cpfPessoaFisica;
    private LocalDateTime dataCadastro;
    private Boolean enabled = false;
    private LocalDateTime dtAtivacao;
    private String userNameAtivacao;
}

