package com.ibmec.backend.malldelivery.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Loja {
    private int idLojista;
    private String nome;
    private String email;
    private String telefone;
    private String cnpj;
    private LocalDateTime dataCadastro;
    private String banner;
    private String perfil;
    private String urlFacebook;
    private String urlInstagram;
    private String urlTwitter;
    private int numMaxProduto;
    private int abaProdAdd;

    private List<DadoBancario> dadosBancarios;
    private List<Endereco> enderecos;
}
