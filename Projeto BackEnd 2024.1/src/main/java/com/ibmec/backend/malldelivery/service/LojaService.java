package com.ibmec.backend.malldelivery.service;

import com.ibmec.backend.malldelivery.model.Loja;
import com.ibmec.backend.malldelivery.repository.LojaRepository;
import com.ibmec.backend.malldelivery.request.LojistaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LojaService {

    @Autowired
    private LojaRepository repository;
    public Loja criarLoja(LojistaRequest request) throws Exception{
        Loja loja = Loja.fromRequest(request);
        return loja;
    }
}
