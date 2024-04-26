package com.ibmec.backend.malldelivery.contoller;

import com.ibmec.backend.malldelivery.request.LojistaRequest;
import com.ibmec.backend.malldelivery.service.LojaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lojista")
public class LojistaController {

    @Autowired
    private LojaService lojaService;
    @PostMapping
    public ResponseEntity criarLoja(@RequestBody @Valid LojistaRequest request) throws Exception {
        return new ResponseEntity(lojaService.criarLoja(request), (HttpStatus.OK));
    }
}
