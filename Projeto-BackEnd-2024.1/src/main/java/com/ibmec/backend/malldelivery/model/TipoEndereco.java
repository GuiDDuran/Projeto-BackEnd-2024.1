package com.ibmec.backend.malldelivery.model;

public enum TipoEndereco {
    RESIDENCIAL(1),
    COMERCIAL(2);

    private final int id;

    TipoEndereco(int id){
        this.id = id;
    }

    public String fromString(TipoEndereco tp){
        return tp.toString();
    }
}
