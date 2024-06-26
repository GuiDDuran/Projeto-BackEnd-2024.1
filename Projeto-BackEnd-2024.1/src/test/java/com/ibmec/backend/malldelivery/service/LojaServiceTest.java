package com.ibmec.backend.malldelivery.service;

import com.ibmec.backend.malldelivery.exception.LojaException;
import com.ibmec.backend.malldelivery.model.Loja;
import com.ibmec.backend.malldelivery.repository.LojaRepository;
import com.ibmec.backend.malldelivery.request.LojaAtivacaoRequest;
import com.ibmec.backend.malldelivery.request.LojaRequest;
import com.ibmec.backend.malldelivery.response.LojaResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class LojaServiceTest {

    @Autowired
    private LojaService service;

    @MockBean
    private LojaRepository lojaRepository;

    private LojaRequest request;

    @BeforeEach
    public void setUp() {
        request = new LojaRequest();
        request.setNomeLoja("Loja 1");
        request.setCnpj("99.999.999/9999-99");
        request.setTelefoneLoja("(99)99999-9999");
        request.setEmailLoja("guilherme.d.gea@gmail.com");
        request.setBannerLoja("banner");
        request.setPerfilLoja("perfil");
        request.setUrlFacebook("facebook");
        request.setUrlInstagram("instagram");
        request.setUrlTwitter("twitter");

        request.setNomeBanco("Santander");
        request.setAgencia("9999");
        request.setConta("99999-9");
        request.setCodigoBanco("999");
        request.setTipoConta("CI");

        request.setCep("99999-999");
        request.setLogradouro("Rua 1");
        request.setComplemento("Complemento 1");
        request.setBairro("Bairro 1");
        request.setCidade("Cidade 1");
        request.setEstado("Estado 1");
        request.setPais("Pais 1");
        request.setDescricao("Descricao 1");

        request.setNomePessoaFisica("Guilherme");
        request.setSobrenomePessoaFisica("Gea");
        request.setCpfPessoaFisica("999.999.999-99");
        request.setTelefonePessoaFisica("(99)99999-9999");
        request.setEmailPessoaFisica("guilherme.d.gea@gmail.com");
    }

    @Test
    public void DeveCriarUmaLojaComSucesso() throws Exception{

        given(this.lojaRepository.findByCnpj(request.getCnpj())).willReturn(Optional.empty());

        LojaResponse response = this.service.criarLoja(request);

        assertNotNull(response);
        assertEquals(response.getNomeLoja(), request.getNomeLoja());
        Assertions.assertFalse(response.getEnabled());
        assertNotNull(response.getDataCadastro());
    }

    @Test
    public void NaoDeveCriarUmaLojaComCnpjJaCadastrado() throws Exception {
        given(this.lojaRepository.findByCnpj(request.getCnpj())).willReturn(Optional.of(new Loja()));

        Assertions.assertThrowsExactly(LojaException.class, () -> {
            LojaResponse response = this.service.criarLoja(this.request);
        });

    }

    @Test
    public void DeveObterUmaLojaPorIdComSucesso() {
        int id = 1;
        given(this.lojaRepository.findById(id)).willReturn(Optional.of(new Loja()));

        LojaResponse response = this.service.obterLojistaPorId(id);

        assertNotNull(response);
    }

    @Test
    public void NaoDeveObterUmaLojaPorIdQuandoNaoEncontrarNaBase() {
        int id = 1;
        given(this.lojaRepository.findById(id)).willReturn(Optional.empty());

        LojaResponse response = this.service.obterLojistaPorId(id);

        Assertions.assertNull(response);
    }

    @Test
    public void DeveAtivarLojistaComSucesso() throws Exception{
        int id = 1;
        given(this.lojaRepository.findById(id)).willReturn(Optional.of(new Loja()));

        LojaAtivacaoRequest request = new LojaAtivacaoRequest();
        request.setEnabled(true);
        request.setUserNameAtivacao("User");

        LojaResponse response = this.service.ativarLojista(id, request);

        assertNotNull(response);
        Assertions.assertTrue(response.getEnabled());
        Assertions.assertSame(response.getUserNameAtivacao(), "User");
        assertNotNull(response.getDtAtivacao());
    }

    @Test
    public void NaoDeveAtivarLojistaQuandoNaoEncontrarNaBase() throws Exception{
        int id = 1;
        given(this.lojaRepository.findById(id)).willReturn(Optional.empty());

        Assertions.assertThrowsExactly(LojaException.class, () -> {
            LojaAtivacaoRequest request = new LojaAtivacaoRequest();
            request.setEnabled(true);
            request.setUserNameAtivacao("User");
            LojaResponse response = this.service.ativarLojista(id, request);
        });
    }

    @Test
    public void DeveAtualizarDadosLojistaComSucesso() throws Exception {
        int id = 1;
        given(this.lojaRepository.findById(id)).willReturn(Optional.of(new Loja()));

        LojaResponse response = this.service.atualizarDadosLojista(id, request);

        assertNotNull(response);
        assertEquals(response.getNomeLoja(), request.getNomeLoja());
    }

    @Test
    public void NaoDeveAtualizarDadosLojistaQuandoNaoEncontrarNaBase() throws Exception {
        int id = 1;
        given(this.lojaRepository.findById(id)).willReturn(Optional.empty());

        Assertions.assertThrowsExactly(LojaException.class, () -> {
            LojaResponse response = this.service.atualizarDadosLojista(id, request);
        });
    }

    @Test
    public void DeveDeletarLojaComSucesso() throws Exception {
        int id = 1;
        given(this.lojaRepository.findById(id)).willReturn(Optional.of(new Loja()));

        LojaResponse response = this.service.deletarLoja(id);

        assertNotNull(response);
        verify(this.lojaRepository).deleteById(id);
    }

    @Test
    public void NaoDeveDeletarLojaQuandoNaoEncontrarNaBase() throws Exception {
        int id = 1;
        given(this.lojaRepository.findById(id)).willReturn(Optional.empty());


        Assertions.assertThrowsExactly(LojaException.class, () -> {
            LojaResponse response = this.service.deletarLoja(id);
        });
    }
}
