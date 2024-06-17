package com.ibmec.backend.malldelivery.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibmec.backend.malldelivery.exception.LojaException;
import com.ibmec.backend.malldelivery.model.*;
import com.ibmec.backend.malldelivery.request.LojaAtivacaoRequest;
import com.ibmec.backend.malldelivery.request.LojaRequest;
import com.ibmec.backend.malldelivery.service.LojaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(controllers = LojaController.class)
public class LojaControllerTest {
    @MockBean
    private LojaService lojaService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

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
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
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
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    public void deveConsultarLojistaPorIdRetornandoNotFound() throws Exception{
        int id = 1;
        given(this.lojaService.obterLojistaPorId(id)).willReturn(null);
        mvc.perform(MockMvcRequestBuilders.get("/lojista/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
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
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    public void deveConsultarLojistaPorCnpjRetornandoNotFound() throws Exception{
        String cnpj = "99.999.999/9999-99";
        given(this.lojaService.obterLojistaPorCnpj(cnpj)).willReturn(null);
        mvc.perform(MockMvcRequestBuilders.get("/lojista/busca?cnpj=" + cnpj)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    public void deveHabilitarLojistaComSucesso() throws Exception{
        int id = 1;
        LojaAtivacaoRequest ativacaoRequest = new LojaAtivacaoRequest();
        ativacaoRequest.setEnabled(true);
        ativacaoRequest.setUserNameAtivacao("User");

        given(this.lojaService.ativarLojista(id, ativacaoRequest)).willReturn(Loja.toResponse(this.loja));
        mvc.perform(MockMvcRequestBuilders.patch("/lojista/habilitar/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ativacaoRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(this.loja.getId())))
                .andExpect(jsonPath("$.nome", is(this.loja.getNome())));
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    public void naoDeveHabilitarLojistaRetornandoNotFound() throws Exception{
        int id = 1;
        LojaAtivacaoRequest ativacaoRequest = new LojaAtivacaoRequest();
        ativacaoRequest.setEnabled(true);
        ativacaoRequest.setUserNameAtivacao("User");

        given(this.lojaService.ativarLojista(id, ativacaoRequest)).willThrow(new LojaException("id", "Identificador da loja não encontrado"));
        mvc.perform(MockMvcRequestBuilders.patch("/lojista/habilitar/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ativacaoRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    public void deveAtualizarDadosLojistaComSucesso() throws Exception{
        int id = 1;

        LojaRequest lojaRequest = new LojaRequest();
        lojaRequest.setNome("Nova Loja");
        lojaRequest.setCnpj("99.999.999/9999-97");
        lojaRequest.setTelefone("(99)99999-9998");
        lojaRequest.setEmail("abc@gmail.com");
        lojaRequest.setBanner("novo_banner");
        lojaRequest.setPerfil("novo_perfil");
        lojaRequest.setUrlFacebook("nova_facebook");
        lojaRequest.setUrlInstagram("nova_instagram");
        lojaRequest.setUrlTwitter("nova_twitter");
        lojaRequest.setNomeBanco("Banco do Brasil");
        lojaRequest.setAgencia("8888");
        lojaRequest.setConta("88888-8");
        lojaRequest.setCodigoBanco("888");
        lojaRequest.setTipoConta("CP");
        lojaRequest.setLogradouro("Nova Rua");
        lojaRequest.setBairro("Novo Bairro");
        lojaRequest.setCidade("Nova Cidade");
        lojaRequest.setCep("88888-888");
        lojaRequest.setPais("Novo País");
        lojaRequest.setEstado("Novo Estado");
        lojaRequest.setComplemento("Novo Complemento");
        lojaRequest.setDescricao("Nova Descrição");
        lojaRequest.setNomePessoaFisica("João");
        lojaRequest.setSobrenomePessoaFisica("Silva");
        lojaRequest.setEmailPessoaFisica("joao@gmail.com");
        lojaRequest.setTelefonePessoaFisica("(88)88888-8888");
        lojaRequest.setCpfPessoaFisica("888.888.888-88");

        given(this.lojaService.atualizarDadosLojista(id, lojaRequest)).willReturn(Loja.toResponse(this.loja));
        mvc.perform(MockMvcRequestBuilders.put("/lojista/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lojaRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(this.loja.getId())))
                .andExpect(jsonPath("$.nome", is(this.loja.getNome())));
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    public void naoDeveAtualizarDadosLojistaRetornandoNotFound() throws Exception{
        int id = 1;

        LojaRequest lojaRequest = new LojaRequest();
        lojaRequest.setNome("Test Loja");
        lojaRequest.setEmail("test@loja.com");
        lojaRequest.setTelefone("(11)99999-9999");
        lojaRequest.setCnpj("00.000.000/0000-00");
        lojaRequest.setBanner("banner.png");
        lojaRequest.setPerfil("perfil");
        lojaRequest.setNomeBanco("Banco Teste");
        lojaRequest.setAgencia("0000");
        lojaRequest.setConta("00000-0");
        lojaRequest.setCodigoBanco("000");
        lojaRequest.setTipoConta("CORRENTE");
        lojaRequest.setLogradouro("Rua Teste");
        lojaRequest.setBairro("Bairro Teste");
        lojaRequest.setCidade("Cidade Teste");
        lojaRequest.setCep("00000-000");
        lojaRequest.setPais("Brasil");
        lojaRequest.setEstado("SP");
        lojaRequest.setComplemento("Complemento");
        lojaRequest.setNomePessoaFisica("Teste");
        lojaRequest.setSobrenomePessoaFisica("Testando");
        lojaRequest.setEmailPessoaFisica("testando@test.com");
        lojaRequest.setTelefonePessoaFisica("(11)99999-9999");
        lojaRequest.setCpfPessoaFisica("000.000.000-00");

        given(this.lojaService.atualizarDadosLojista(id, lojaRequest))
                .willThrow(new LojaException("id", "Identificador da loja não encontrado"));

        mvc.perform(MockMvcRequestBuilders.put("/lojista/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(lojaRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    public void deveDeletarLojaComSucesso() throws Exception{
        int id = 1;
        given(this.lojaService.deletarLoja(id)).willReturn(Loja.toResponse(this.loja));
        mvc.perform(MockMvcRequestBuilders.delete("/lojista/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    public void naoDeveDeletarLojaRetornandoNotFound() throws Exception{
        int id = 1;
        given(this.lojaService.deletarLoja(id)).willReturn(null);
        mvc.perform(MockMvcRequestBuilders.delete("/lojista/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    public void deveCriarLojaComSucesso() throws Exception{
        LojaRequest lojaRequest = new LojaRequest();
        lojaRequest.setNome("Nova Loja");
        lojaRequest.setEmail("abcde@gmail.com");
        lojaRequest.setTelefone("(99)99999-9999");
        lojaRequest.setCnpj("99.999.999/9999-98");
        lojaRequest.setBanner("novo_banner");
        lojaRequest.setPerfil("novo_perfil");
        lojaRequest.setUrlFacebook("nova_facebook");
        lojaRequest.setUrlInstagram("nova_instagram");
        lojaRequest.setUrlTwitter("nova_twitter");
        lojaRequest.setNomeBanco("Banco do Brasil");
        lojaRequest.setAgencia("8888");
        lojaRequest.setConta("88888-8");
        lojaRequest.setCodigoBanco("888");
        lojaRequest.setTipoConta("CP");
        lojaRequest.setLogradouro("Nova Rua");
        lojaRequest.setBairro("Novo Bairro");
        lojaRequest.setCidade("Nova Cidade");
        lojaRequest.setCep("88888-888");
        lojaRequest.setPais("Novo País");
        lojaRequest.setEstado("Novo Estado");
        lojaRequest.setComplemento("Novo Complemento");
        lojaRequest.setDescricao("Nova Descrição");
        lojaRequest.setNomePessoaFisica("João");
        lojaRequest.setSobrenomePessoaFisica("Silva");
        lojaRequest.setEmailPessoaFisica("zeca@gmail.com");
        lojaRequest.setTelefonePessoaFisica("(88)88888-8888");
        lojaRequest.setCpfPessoaFisica("888.888.888-88");

        given(this.lojaService.criarLoja(lojaRequest)).willReturn(Loja.toResponse(this.loja));

        mvc.perform(MockMvcRequestBuilders.post("/lojista")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lojaRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(this.loja.getId())))
                .andExpect(jsonPath("$.nome", is(this.loja.getNome())));
    }

    @Test
    @WithMockUser(username = "admin", password = "pwd", roles = "ADMIN")
    public void naoDeveCriarLojaComCnpjJaCadastrado() throws Exception{
        LojaRequest lojaRequest = new LojaRequest();
        lojaRequest.setNome("Nova Loja");
        lojaRequest.setEmail("abcde@gmail.com");
        lojaRequest.setTelefone("(99)99999-9999");
        lojaRequest.setCnpj("99.999.999/9999-98");
        lojaRequest.setBanner("novo_banner");
        lojaRequest.setPerfil("novo_perfil");
        lojaRequest.setUrlFacebook("nova_facebook");
        lojaRequest.setUrlInstagram("nova_instagram");
        lojaRequest.setUrlTwitter("nova_twitter");
        lojaRequest.setNomeBanco("Banco do Brasil");
        lojaRequest.setAgencia("8888");
        lojaRequest.setConta("88888-8");
        lojaRequest.setCodigoBanco("888");
        lojaRequest.setTipoConta("CP");
        lojaRequest.setLogradouro("Nova Rua");
        lojaRequest.setBairro("Novo Bairro");
        lojaRequest.setCidade("Nova Cidade");
        lojaRequest.setCep("88888-888");
        lojaRequest.setPais("Novo País");
        lojaRequest.setEstado("Novo Estado");
        lojaRequest.setComplemento("Novo Complemento");
        lojaRequest.setDescricao("Nova Descrição");
        lojaRequest.setNomePessoaFisica("João");
        lojaRequest.setSobrenomePessoaFisica("Silva");
        lojaRequest.setEmailPessoaFisica("zeca@gmail.com");
        lojaRequest.setTelefonePessoaFisica("(88)88888-8888");
        lojaRequest.setCpfPessoaFisica("888.888.888-88");

        given(this.lojaService.criarLoja(lojaRequest)).willThrow(new LojaException("cnpj", "CNPJ já cadastrado"));

        mvc.perform(MockMvcRequestBuilders.post("/lojista")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lojaRequest)))
                .andExpect(status().isNotFound());
    }
}
