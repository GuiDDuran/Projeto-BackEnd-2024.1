package com.ibmec.backend.malldelivery.model;

import com.ibmec.backend.malldelivery.exception.LojaException;
import com.ibmec.backend.malldelivery.request.LojistaRequest;
import com.ibmec.backend.malldelivery.response.LojistaResponse;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "loja")
public class Loja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String nome;

    @Column
    private String email;

    @Column
    private String telefone;

    @Column
    private String cnpj;

    @Column
    private LocalDateTime dataCadastro;

    @Column
    private String banner;

    @Column
    private String perfil;

    @Column
    private String urlFacebook;

    @Column
    private String urlInstagram;

    @Column
    private String urlTwitter;

    @Column
    private int numMaxProduto;

    @Column
    private int abaProdAdd;

    @OneToMany
    @JoinColumn(name = "id_loja", referencedColumnName = "id")
    private List<DadoBancario> dadosBancarios = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "id_loja", referencedColumnName = "id")
    private List<Endereco> enderecos = new ArrayList<>();

    @Column
    private Boolean Enabled = false;

    @Column
    private LocalDateTime dtAtivacao;

    @Column
    private String userNameAtivacao;

    public static Loja fromRequest(LojistaRequest request) throws Exception {
        Loja loja = new Loja();
        loja.setNome(request.getNome());
        loja.setEmail(request.getEmail());
        loja.setTelefone(request.getTelefone());
        loja.setCnpj(request.getCnpj());
        loja.setBanner(request.getBanner());
        loja.setPerfil(request.getPerfil());
        loja.setUrlFacebook(request.getUrlFacebook());
        loja.setUrlInstagram(request.getUrlInstagram());
        loja.setUrlTwitter(request.getUrlTwitter());
        loja.setNumMaxProduto(request.getNumMaxProduto());
        loja.setAbaProdAdd(request.getAbaProdAdd());

        DadoBancario dadoBancario = new DadoBancario();

        if(request.getTipoConta().equals("CC")){
            dadoBancario.setTipoConta(TipoConta.CONTA_CORRENTE);
        }
        else if (request.getTipoConta().equals("CP")){
            dadoBancario.setTipoConta(TipoConta.CONTA_POUPANCA);
        }
        else if (request.getTipoConta().equals("CI")){
            dadoBancario.setTipoConta(TipoConta.CONTA_INVESTIMENTO);
        }
        else{
            throw new LojaException("tipoConta", "Tipo de conta inválido, valores válidos: CC, CP, CI");
        }
        dadoBancario.setNomeBanco(request.getNomeBanco());
        dadoBancario.setAgencia(request.getAgencia());
        dadoBancario.setConta(request.getConta());
        dadoBancario.setCodigoBanco(request.getCodigoBanco());
        loja.getDadosBancarios().add(dadoBancario);

        Endereco endereco = new Endereco();
        if(request.getTipoEndereco().equals("Residencial")){
            endereco.setTipoEndereco(TipoEndereco.RESIDENCIAL);
        }
        else if (request.getTipoEndereco().equals("Comercial")){
            endereco.setTipoEndereco(TipoEndereco.COMERCIAL);
        }
        else{
            throw new LojaException("tipoEndereco", "Tipo de endereço inválido");
        }
        endereco.setCep(request.getCep());
        endereco.setLogradouro(request.getLogradouro());
        endereco.setComplemento(request.getComplemento());
        endereco.setBairro(request.getBairro());
        endereco.setCidade(request.getCidade());
        endereco.setEstado(request.getEstado());
        endereco.setPais(request.getPais());
        loja.getEnderecos().add(endereco);

        return loja;
    }

    public static Loja fromRequest(Loja loja, LojistaRequest request) throws LojaException {

        loja.setNome(request.getNome());
        loja.setEmail(request.getEmail());
        loja.setTelefone(request.getTelefone());
        loja.setCnpj(request.getCnpj());
        loja.setBanner(request.getBanner());
        loja.setPerfil(request.getPerfil());
        loja.setUrlFacebook(request.getUrlFacebook());
        loja.setUrlInstagram(request.getUrlInstagram());
        loja.setUrlTwitter(request.getUrlTwitter());
        loja.setNumMaxProduto(request.getNumMaxProduto());
        loja.setAbaProdAdd(request.getAbaProdAdd());

        DadoBancario dadoBancario = loja.dadosBancarios.getFirst();

        if(request.getTipoConta().equals("CC")){
            dadoBancario.setTipoConta(TipoConta.CONTA_CORRENTE);
        }
        else if (request.getTipoConta().equals("CP")){
            dadoBancario.setTipoConta(TipoConta.CONTA_POUPANCA);
        }
        else if (request.getTipoConta().equals("CI")){
            dadoBancario.setTipoConta(TipoConta.CONTA_INVESTIMENTO);
        }
        else{
            throw new LojaException("tipoConta", "Tipo de conta inválido, valores válidos: CC, CP, CI");
        }
        dadoBancario.setNomeBanco(request.getNomeBanco());
        dadoBancario.setAgencia(request.getAgencia());
        dadoBancario.setConta(request.getConta());
        dadoBancario.setCodigoBanco(request.getCodigoBanco());

        Endereco endereco = loja.getEnderecos().getFirst();

        if(request.getTipoEndereco().equals("Residencial")){
            endereco.setTipoEndereco(TipoEndereco.RESIDENCIAL);
        }
        else if (request.getTipoEndereco().equals("Comercial")){
            endereco.setTipoEndereco(TipoEndereco.COMERCIAL);
        }
        else{
            throw new LojaException("tipoEndereco", "Tipo de endereço inválido");
        }

        endereco.setCep(request.getCep());
        endereco.setLogradouro(request.getLogradouro());
        endereco.setComplemento(request.getComplemento());
        endereco.setBairro(request.getBairro());
        endereco.setCidade(request.getCidade());
        endereco.setEstado(request.getEstado());
        endereco.setPais(request.getPais());

        return loja;
    }

    public static LojistaResponse toResponse(Loja loja){
        LojistaResponse response = new LojistaResponse();

        response.setNome(loja.getNome());
        response.setEmail(loja.getEmail());
        response.setTelefone(loja.getTelefone());
        response.setCnpj(loja.getCnpj());
        response.setBanner(loja.getBanner());
        response.setPerfil(loja.getPerfil());
        response.setUrlFacebook(loja.getUrlFacebook());
        response.setUrlInstagram(loja.getUrlInstagram());
        response.setUrlTwitter(loja.getUrlTwitter());
        response.setNumMaxProduto(loja.getNumMaxProduto());
        response.setAbaProdAdd(loja.getAbaProdAdd());
        response.setId(loja.getId());
        response.setDataCadastro(loja.getDataCadastro());
        response.setEnabled(loja.getEnabled());
        response.setDtAtivacao(loja.getDtAtivacao());
        response.setUserNameAtivacao(loja.getUserNameAtivacao());

        if (loja.getDadosBancarios().size() > 0){
            response.setNomeBanco(loja.getDadosBancarios().getFirst().getNomeBanco());
            response.setAgencia(loja.getDadosBancarios().getFirst().getAgencia());
            response.setConta(loja.getDadosBancarios().getFirst().getConta());
            response.setCodigoBanco(loja.getDadosBancarios().getFirst().getCodigoBanco());
            response.setTipoConta(loja.getDadosBancarios().getFirst().getTipoConta().toString());
        }

        if (loja.getEnderecos().size() > 0){
            response.setLogradouro(loja.getEnderecos().getFirst().getLogradouro());
            response.setBairro(loja.getEnderecos().getFirst().getBairro());
            response.setCep(loja.getEnderecos().getFirst().getCep());
            response.setCidade(loja.getEnderecos().getFirst().getCidade());
            response.setComplemento(loja.getEnderecos().getFirst().getComplemento());
            response.setEstado(loja.getEnderecos().getFirst().getEstado());
            response.setPais(loja.getEnderecos().getFirst().getPais());
        }

        return response;

    }
}
