package com.ibmec.backend.malldelivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibmec.backend.malldelivery.contoller.LojaController;
import com.ibmec.backend.malldelivery.model.*;
import com.ibmec.backend.malldelivery.service.LojaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;

@AutoConfigureMockMvc
@WebMvcTest(controllers = LojaController.class)
public class LojaControllerTest {
    @MockBean
    private LojaService lojaService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Loja loja;

    @BeforeEach
    public void setUp() {
        loja = new Loja();
        loja.setId(1);
        loja.setNome("Loja 1");
        loja.setCnpj("99.999.999/9999-99");
        loja.setTelefone("(99)99999-9999");
        loja.setEmail("guilherme.d.gea@gmail.com");
        loja.setBanner("banner");
        loja.setPerfil("perfil");
        loja.setUrlFacebook("facebook");
        loja.setUrlInstagram("instagram");
        loja.setUrlTwitter("twitter");

        Endereco endereco = new Endereco();
        endereco.setCep("99999-999");
        endereco.setLogradouro("Rua 1");
        endereco.setComplemento("Complemento 1");
        endereco.setBairro("Bairro 1");
        endereco.setCidade("Cidade 1");
        endereco.setEstado("Estado 1");
        endereco.setPais("Pais 1");
        endereco.setDescricao("Descricao 1");
        endereco.setTipoEndereco(TipoEndereco.RESIDENCIAL);
        loja.getEnderecos().add(endereco);

        DadoBancario dadoBancario = new DadoBancario();
        dadoBancario.setNomeBanco("Santander");
        dadoBancario.setAgencia("9999");
        dadoBancario.setConta("99999-9");
        dadoBancario.setCodigoBanco("999");
        dadoBancario.setTipoConta(TipoConta.CONTA_CORRENTE);
        loja.getDadosBancarios().add(dadoBancario);

        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setNomePessoaFisica("Guilherme");
        pessoaFisica.setSobrenomePessoaFisica("Gea");
        pessoaFisica.setCpfPessoaFisica("999.999.999-99");
        pessoaFisica.setTelefonePessoaFisica("(99)99999-9999");
        pessoaFisica.setEmailPessoaFisica("guilherme.d.gea@gmail.com");

    }

    @Test
    public void deveConsultarLojistaPorIdComSucesso() throws Exception{
        int id = 1;
        given(this.lojaService.obterLojistaPorId(id)).willReturn(Loja.toResponse(this.loja));
        mvc.perform(MockMvcRequestBuilders.get("/lojista/" + id)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.nome", is(this.loja.getNome())));
    }

    @Test
    public void deveConsultarLojistaPorIdRetornandoNotFound() throws Exception{
        int id = 1;
        given(this.lojaService.obterLojistaPorId(id)).willReturn(null);
        mvc.perform(MockMvcRequestBuilders.get("/lojista/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deveConsultarLojistaPorCnpjComSucesso() throws Exception{
        String cnpj = "99.999.999/9999-99";
        given(this.lojaService.obterLojistaPorCnpj(cnpj)).willReturn(Loja.toResponse(this.loja));
        mvc.perform(MockMvcRequestBuilders.get("/lojista/busca?cnpj=" + cnpj)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(this.loja.getId())))
                .andExpect(jsonPath("$.nome", is(this.loja.getNome())));
    }

    @Test
    public void deveConsultarLojistaPorCnpjRetornandoNotFound() throws Exception{
        String cnpj = "99.999.999/9999-99";
        given(this.lojaService.obterLojistaPorCnpj(cnpj)).willReturn(null);
        mvc.perform(MockMvcRequestBuilders.get("/lojista/busca?cnpj=" + cnpj)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deveHabilitarLojistaComSucesso() throws Exception{
        int id = 1;
        given(this.lojaService.ativarLojista(id, null)).willReturn(Loja.toResponse(this.loja));
        mvc.perform(MockMvcRequestBuilders.patch("/lojista/habilitar/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(this.loja.getId())))
                .andExpect(jsonPath("$.nome", is(this.loja.getNome())));
    }

    @Test
    public void naoDeveHabilitarLojistaRetornandoNotFound() throws Exception{
        int id = 1;
        given(this.lojaService.ativarLojista(id, null)).willReturn(null);
        mvc.perform(MockMvcRequestBuilders.patch("/lojista/habilitar/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

//    @Test
//    public void deveDesabilitarLojistaComSucesso() throws Exception{
//        int id = 1;
//        given(this.lojaService.desativarLojista(id)).willReturn(Loja.toResponse(this.loja));
//        mvc.perform(MockMvcRequestBuilders.patch("/lojista/desabilitar/" + id)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(this.loja.getId())))
//                .andExpect(jsonPath("$.nome", is(this.loja.getNome())));
//    }

//    @Test
//    public void naoDeveDesabilitarLojistaRetornandoNotFound() throws Exception{
//        int id = 1;
//        given(this.lojaService.desativarLojista(id)).willReturn(null);
//        mvc.perform(MockMvcRequestBuilders.patch("/lojista/desabilitar/" + id)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//    }

    @Test
    public void deveAtualizarDadosLojistaComSucesso() throws Exception{
        int id = 1;
        given(this.lojaService.atualizarDadosLojista(id, null)).willReturn(Loja.toResponse(this.loja));
        mvc.perform(MockMvcRequestBuilders.put("/lojista/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(this.loja.getId())))
                .andExpect(jsonPath("$.nome", is(this.loja.getNome())));
    }

    @Test
    public void naoDeveAtualizarDadosLojistaRetornandoNotFound() throws Exception{
        int id = 1;
        given(this.lojaService.atualizarDadosLojista(id, null)).willReturn(null);
        mvc.perform(MockMvcRequestBuilders.put("/lojista/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deveDeletarLojaComSucesso() throws Exception{
        int id = 1;
        given(this.lojaService.deletarLoja(id)).willReturn(Loja.toResponse(this.loja));
        mvc.perform(MockMvcRequestBuilders.delete("/lojista/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void naoDeveDeletarLojaRetornandoNotFound() throws Exception{
        int id = 1;
        given(this.lojaService.deletarLoja(id)).willReturn(null);
        mvc.perform(MockMvcRequestBuilders.delete("/lojista/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deveCriarLojaComSucesso() throws Exception{
        given(this.lojaService.criarLoja(null)).willReturn(Loja.toResponse(this.loja));
        mvc.perform(MockMvcRequestBuilders.post("/lojista")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(this.loja.getId())))
                .andExpect(jsonPath("$.nome", is(this.loja.getNome())));
    }

    @Test
    public void naoDeveCriarLojaRetornandoNotFound() throws Exception{
        given(this.lojaService.criarLoja(null)).willReturn(null);
        mvc.perform(MockMvcRequestBuilders.post("/lojista")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
