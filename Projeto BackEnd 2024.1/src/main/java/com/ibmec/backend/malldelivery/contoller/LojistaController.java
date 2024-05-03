package com.ibmec.backend.malldelivery.contoller;

import com.ibmec.backend.malldelivery.exception.LojaException;
import com.ibmec.backend.malldelivery.request.LojistaAtivacaoRequest;
import com.ibmec.backend.malldelivery.request.LojistaRequest;
import com.ibmec.backend.malldelivery.response.LojistaResponse;
import com.ibmec.backend.malldelivery.service.LojaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lojista")
public class LojistaController {

    @Autowired
    private LojaService lojaService;
    @PostMapping
    public ResponseEntity<LojistaResponse> criarLoja(@RequestBody @Valid LojistaRequest request) throws Exception {

        LojistaResponse response = this.lojaService.criarLoja(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<LojistaResponse> obterLojistaPorId(@PathVariable int id) {
        LojistaResponse response = this.lojaService.obterLojistaPorId(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("busca/{cnpj}")
    public ResponseEntity<LojistaResponse> obterLojistaPorCnpj(@PathVariable String cnpj) {
        LojistaResponse response = this.lojaService.obterLojistaPorCnpj(cnpj);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("habilitar/{id}")
    public ResponseEntity<LojistaResponse> ativarLojista(@PathVariable int id, @RequestBody @Valid LojistaAtivacaoRequest request) throws LojaException {
        LojistaResponse response = this.lojaService.ativarLojista(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<LojistaResponse> atualizarDadosLojista(@PathVariable int id, @RequestBody @Valid LojistaRequest request) throws LojaException{
        LojistaResponse response = this.lojaService.atualizarDadosLojista(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
