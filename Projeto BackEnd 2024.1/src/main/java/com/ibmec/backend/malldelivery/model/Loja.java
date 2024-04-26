package com.ibmec.backend.malldelivery.model;

import com.ibmec.backend.malldelivery.request.LojistaRequest;
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
            throw new Exception("Tipo de conta inválido");
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
            throw new Exception("Tipo de endereço inválido");
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
}
