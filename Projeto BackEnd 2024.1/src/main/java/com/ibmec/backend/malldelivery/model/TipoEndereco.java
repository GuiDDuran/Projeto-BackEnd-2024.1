package com.ibmec.backend.malldelivery.model;

public enum TipoEndereco {
    Residencial(1),
    Comercial(2);

    private final int id;

    TipoEndereco(int id){
        this.id = id;
    }

    public String fromString(TipoEndereco tp){
        return tp.toString();
    }
}
