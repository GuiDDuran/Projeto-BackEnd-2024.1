package com.ibmec.backend.malldelivery.model;

import lombok.Data;

@Data
public class DadoBancario {
    private int idDadoBancario;
    private String nomeBanco;
    private String agencia;
    private String conta;
    private String codigoBanco;
    private TipoConta tipoConta = TipoConta.CONTA_CORRENTE;
}
