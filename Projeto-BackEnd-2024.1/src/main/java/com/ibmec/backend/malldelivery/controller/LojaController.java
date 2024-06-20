package com.ibmec.backend.malldelivery.controller;

import com.ibmec.backend.malldelivery.exception.LojaException;
import com.ibmec.backend.malldelivery.request.LojaAtivacaoRequest;
import com.ibmec.backend.malldelivery.request.LojaRequest;
import com.ibmec.backend.malldelivery.response.LojaResponse;
import com.ibmec.backend.malldelivery.service.LojaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loja")
public class LojaController {

    @Autowired
    private LojaService lojaService;
    @PostMapping
    public ResponseEntity<LojaResponse> criarloja(@RequestBody @Valid LojaRequest request) throws Exception {
        LojaResponse response = this.lojaService.criarLoja(request);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<LojaResponse> obterLojistaPorId(@PathVariable int id) {
        LojaResponse response = this.lojaService.obterLojistaPorId(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/busca")
    public ResponseEntity<LojaResponse> obterLojistaPorCnpj(@RequestParam String cnpj) {
        LojaResponse response = this.lojaService.obterLojistaPorCnpj(cnpj);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("habilitar/{id}")
    public ResponseEntity<LojaResponse> ativarLojista(@PathVariable int id, @RequestBody @Valid LojaAtivacaoRequest request) throws LojaException {

        LojaResponse response = this.lojaService.ativarLojista(id, request);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<LojaResponse> atualizarDadosLojista(@PathVariable int id, @RequestBody @Valid LojaRequest request) throws LojaException{

        LojaResponse response = this.lojaService.atualizarDadosLojista(id, request);
        if (response == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<LojaResponse> deletarLoja(@PathVariable int id) throws LojaException{
        LojaResponse response = this.lojaService.deletarLoja(id);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
