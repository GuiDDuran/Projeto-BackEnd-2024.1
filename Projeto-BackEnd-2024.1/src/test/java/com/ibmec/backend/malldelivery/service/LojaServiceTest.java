package com.ibmec.backend.malldelivery.service;

import com.ibmec.backend.malldelivery.exception.LojaException;
import com.ibmec.backend.malldelivery.model.Loja;
import com.ibmec.backend.malldelivery.repository.LojaRepository;
import com.ibmec.backend.malldelivery.request.LojistaAtivacaoRequest;
import com.ibmec.backend.malldelivery.request.LojistaRequest;
import com.ibmec.backend.malldelivery.response.LojistaResponse;
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

    private LojistaRequest request;

    @BeforeEach
    public void setUp() {
        request = new LojistaRequest();
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

        LojistaResponse response = this.service.criarLoja(request);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getNome(), request.getNome());
        Assertions.assertNotNull(response.getId());
        Assertions.assertFalse(response.getEnabled());
        Assertions.assertNotNull(response.getDataCadastro());
    }

    @Test
    public void NaoDeveCriarUmaLojaComCnpjJaCadastrado() throws Exception {
        given(this.lojaRepository.findByCnpj(request.getCnpj())).willReturn(Optional.of(new Loja()));

        Assertions.assertThrowsExactly(LojaException.class, () -> {
            LojistaResponse response = this.service.criarLoja(this.request);
        });

    }

    @Test
    public void DeveObterUmaLojaPorIdComSucesso() {
        int id = 1;
        given(this.lojaRepository.findById(id)).willReturn(Optional.of(new Loja()));

        LojistaResponse response = this.service.obterLojistaPorId(id);

        Assertions.assertNotNull(response);
    }

    @Test
    public void NaoDeveObterUmaLojaPorIdQuandoNaoEncontrarNaBase() {
        int id = 1;
        given(this.lojaRepository.findById(id)).willReturn(Optional.empty());

        LojistaResponse response = this.service.obterLojistaPorId(id);

        Assertions.assertNull(response);
    }

    @Test
    public void DeveAtivarLojistaComSucesso() throws Exception{
        int id = 1;
        given(this.lojaRepository.findById(id)).willReturn(Optional.of(new Loja()));

        LojistaAtivacaoRequest request = new LojistaAtivacaoRequest();
        request.setEnabled(true);
        request.setUserNameAtivacao("User");

        LojistaResponse response = this.service.ativarLojista(id, request);

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
            LojistaAtivacaoRequest request = new LojistaAtivacaoRequest();
            request.setEnabled(true);
            request.setUserNameAtivacao("User");
            LojistaResponse response = this.service.ativarLojista(id, request);
        });
    }
}
