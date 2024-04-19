package com.ibmec.backend.malldelivery.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "dado_bancario")
public class DadoBancario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome_conta")
    private String nomeBanco;

    @Column(name = "agencia_conta")
    private String agencia;

    @Column(name = "numero_conta")
    private String conta;

    @Column(name = "codigo_banco")
    private String codigoBanco;

    @Column(name = "tipo_conta")
    private TipoConta tipoConta = TipoConta.CONTA_CORRENTE;
}
