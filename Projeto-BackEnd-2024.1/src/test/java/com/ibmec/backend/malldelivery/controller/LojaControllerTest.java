package com.ibmec.backend.malldelivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibmec.backend.malldelivery.contoller.LojaController;
import com.ibmec.backend.malldelivery.model.DadoBancario;
import com.ibmec.backend.malldelivery.model.Endereco;
import com.ibmec.backend.malldelivery.model.Loja;
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
        loja.getEnderecos().add(endereco);

        DadoBancario dadoBancario = new DadoBancario();
        dadoBancario.setNomeBanco("Santander");
        dadoBancario.setAgencia("9999");
        dadoBancario.setConta("99999-9");
        dadoBancario.setCodigoBanco("999");
        loja.getDadosBancarios().add(dadoBancario);

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
}
