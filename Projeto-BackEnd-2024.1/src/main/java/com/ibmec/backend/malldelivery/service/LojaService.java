package com.ibmec.backend.malldelivery.service;

import com.ibmec.backend.malldelivery.exception.LojaException;
import com.ibmec.backend.malldelivery.model.DadoBancario;
import com.ibmec.backend.malldelivery.model.Endereco;
import com.ibmec.backend.malldelivery.model.Loja;
import com.ibmec.backend.malldelivery.repository.DadoBancarioRepository;
import com.ibmec.backend.malldelivery.repository.EnderecoRepository;
import com.ibmec.backend.malldelivery.repository.LojaRepository;
import com.ibmec.backend.malldelivery.request.LojaAtivacaoRequest;
import com.ibmec.backend.malldelivery.request.LojaRequest;
import com.ibmec.backend.malldelivery.response.LojaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LojaService {

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private DadoBancarioRepository dadoBancarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public LojaResponse criarLoja(LojaRequest request) throws Exception{
        Loja loja = Loja.fromRequest(request);

        if (this.lojaRepository.findByCnpj(loja.getCnpj()).isEmpty() == false) {
            throw new LojaException("cnpj", "CNPJ já cadastrado");
        }

        Endereco endereco = loja.getEnderecos().getFirst();
        this.enderecoRepository.save(endereco);

        DadoBancario dadoBancario = loja.getDadosBancarios().getFirst();
        this.dadoBancarioRepository.save(dadoBancario);

        loja.setDataCadastro(LocalDateTime.now());

        loja.setEnabled(Boolean.FALSE);

        this.lojaRepository.save(loja);

        LojaResponse response = Loja.toResponse(loja);

        return response;
    }

    public LojaResponse obterLojistaPorId(int id) {
        Optional<Loja> optLoja = this.lojaRepository.findById(id);
        if (optLoja.isEmpty()) {
            return null;
        }
        return Loja.toResponse(optLoja.get());
    }

    public LojaResponse obterLojistaPorCnpj(String cnpj) {
        Optional<Loja> optLoja = this.lojaRepository.findByCnpj(cnpj);
        if (optLoja.isEmpty()) {
            return null;
        }
        return Loja.toResponse(optLoja.get());
    }

    public LojaResponse ativarLojista(int id, LojaAtivacaoRequest request) throws LojaException{
        Optional<Loja> optLoja = this.lojaRepository.findById(id);
        if (optLoja.isEmpty()) {
            throw new LojaException("id", "Identificador da loja não encontrado");
        }
        Loja loja = optLoja.get();
        loja.setEnabled(request.getEnabled());
        loja.setDtAtivacao(LocalDateTime.now());
        loja.setUserNameAtivacao(request.getUserNameAtivacao());
        this.lojaRepository.save(loja);
        return Loja.toResponse(loja);
    }

    public LojaResponse atualizarDadosLojista(int id, LojaRequest request) throws LojaException{
        Optional<Loja> optLoja = this.lojaRepository.findById(id);
        if (optLoja.isEmpty()) {
            throw new LojaException("id", "Identificador da loja não encontrado");
        }
        Loja loja = Loja.fromRequest(optLoja.get(), request);

        Endereco endereco = loja.getEnderecos().getFirst();
        this.enderecoRepository.save(endereco);

        DadoBancario dadoBancario = loja.getDadosBancarios().getFirst();
        this.dadoBancarioRepository.save(dadoBancario);

        this.lojaRepository.save(loja);

        return Loja.toResponse(loja);

    }
}
