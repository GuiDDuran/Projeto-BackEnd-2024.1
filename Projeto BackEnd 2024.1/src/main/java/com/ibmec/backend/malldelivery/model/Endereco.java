package com.ibmec.backend.malldelivery.model;

import lombok.Data;

@Data
public class Endereco {
    private int idEndereco;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String cep;
    private String pais;
    private String estado;
    private String complemento;
    private String descricao;
    private TipoEndereco tipoEndereco;
}
