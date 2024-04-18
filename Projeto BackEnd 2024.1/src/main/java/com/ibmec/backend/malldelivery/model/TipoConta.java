package com.ibmec.backend.malldelivery.model;

public enum TipoConta {
    CONTA_CORRENTE(1),
    CONTA_POUPANCA(2),
    CONTA_INVESTIMENTO(3);

    private final int id;

    TipoConta(int id){
        this.id = id;
    }

    public String fromString(TipoConta tp){
        return tp.toString();
    }

}
