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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

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
        request.setNome("Loja 1");
        request.setCnpj("99.999.999/9999-99");
        request.setTelefone("(99)99999-9999");
        request.setEmail("guilherme.d.gea@gmail.com");
        request.setBanner("banner");
        request.setPerfil("perfil");
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
        request.setTipoEndereco("Residencial");
        request.setDescricao("Descricao 1");
    }

    @Test
    public void DeveCriarUmaLojaComSucesso() throws Exception{

        given(this.lojaRepository.findByCnpj(request.getCnpj())).willReturn(Optional.empty());

        LojaResponse response = this.service.criarLoja(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getNome(), request.getNome());
        Assertions.assertFalse(response.getEnabled());
        Assertions.assertNotNull(response.getDataCadastro());
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

        Assertions.assertNotNull(response);
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

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getEnabled());
        Assertions.assertSame(response.getUserNameAtivacao(), "User");
        Assertions.assertNotNull(response.getDtAtivacao());
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
}
