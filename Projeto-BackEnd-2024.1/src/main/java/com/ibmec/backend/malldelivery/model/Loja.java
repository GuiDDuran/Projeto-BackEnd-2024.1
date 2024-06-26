package com.ibmec.backend.malldelivery.model;

import com.ibmec.backend.malldelivery.exception.LojaException;
import com.ibmec.backend.malldelivery.request.LojaRequest;
import com.ibmec.backend.malldelivery.response.LojaResponse;
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
    private String nomeLoja;

    @Column
    private String emailLoja;

    @Column
    private String telefoneLoja;

    @Column
    private String cnpj;

    @Column
    private LocalDateTime dataCadastro;

    @Column
    private String bannerLoja;

    @Column
    private String perfilLoja;

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

    @OneToMany
    @JoinColumn(name = "id_loja", referencedColumnName = "id")
    private List<PessoaFisica> pessoasFisicas = new ArrayList<>();

    @Column
    private Boolean Enabled = false;

    @Column
    private LocalDateTime dtAtivacao;

    @Column
    private String userNameAtivacao;

    public static Loja fromRequest(LojaRequest request) throws Exception {
        Loja loja = new Loja();
        loja.setNomeLoja(request.getNomeLoja());
        loja.setEmailLoja(request.getEmailLoja());
        loja.setTelefoneLoja(request.getTelefoneLoja());
        loja.setCnpj(request.getCnpj());
        loja.setBannerLoja(request.getBannerLoja());
        loja.setPerfilLoja(request.getPerfilLoja());
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
        endereco.setCep(request.getCep());
        endereco.setLogradouro(request.getLogradouro());
        endereco.setComplemento(request.getComplemento());
        endereco.setBairro(request.getBairro());
        endereco.setCidade(request.getCidade());
        endereco.setEstado(request.getEstado());
        endereco.setPais(request.getPais());
        endereco.setDescricao(request.getDescricao());
        loja.getEnderecos().add(endereco);

        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setNomePessoaFisica(request.getNomePessoaFisica());
        pessoaFisica.setSobrenomePessoaFisica(request.getSobrenomePessoaFisica());
        pessoaFisica.setCpfPessoaFisica(request.getCpfPessoaFisica());
        pessoaFisica.setTelefonePessoaFisica(request.getTelefonePessoaFisica());
        pessoaFisica.setEmailPessoaFisica(request.getEmailPessoaFisica());
        loja.getPessoasFisicas().add(pessoaFisica);

        return loja;
    }

    public static Loja fromRequest(Loja loja, LojaRequest request) throws LojaException {

        loja.setNomeLoja(request.getNomeLoja());
        loja.setEmailLoja(request.getEmailLoja());
        loja.setTelefoneLoja(request.getTelefoneLoja());
        loja.setCnpj(request.getCnpj());
        loja.setBannerLoja(request.getBannerLoja());
        loja.setPerfilLoja(request.getPerfilLoja());
        loja.setUrlFacebook(request.getUrlFacebook());
        loja.setUrlInstagram(request.getUrlInstagram());
        loja.setUrlTwitter(request.getUrlTwitter());
        loja.setNumMaxProduto(request.getNumMaxProduto());
        loja.setAbaProdAdd(request.getAbaProdAdd());

        if(!loja.getDadosBancarios().isEmpty()){
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
        }

        if (!loja.getEnderecos().isEmpty()){
            Endereco endereco = loja.getEnderecos().getFirst();

            endereco.setCep(request.getCep());
            endereco.setLogradouro(request.getLogradouro());
            endereco.setComplemento(request.getComplemento());
            endereco.setBairro(request.getBairro());
            endereco.setCidade(request.getCidade());
            endereco.setEstado(request.getEstado());
            endereco.setPais(request.getPais());
            endereco.setDescricao(request.getDescricao());
        }


        if (!loja.getPessoasFisicas().isEmpty()){
            PessoaFisica pessoaFisica = loja.getPessoasFisicas().getFirst();

            pessoaFisica.setNomePessoaFisica(request.getNomePessoaFisica());
            pessoaFisica.setSobrenomePessoaFisica(request.getSobrenomePessoaFisica());
            pessoaFisica.setCpfPessoaFisica(request.getCpfPessoaFisica());
            pessoaFisica.setTelefonePessoaFisica(request.getTelefonePessoaFisica());
            pessoaFisica.setEmailPessoaFisica(request.getEmailPessoaFisica());
        }
        return loja;
    }

    public static LojaResponse toResponse(Loja loja){
        LojaResponse response = new LojaResponse();

        response.setNomeLoja(loja.getNomeLoja());
        response.setEmailLoja(loja.getEmailLoja());
        response.setTelefoneLoja(loja.getTelefoneLoja());
        response.setCnpj(loja.getCnpj());
        response.setBannerLoja(loja.getBannerLoja());
        response.setPerfilLoja(loja.getPerfilLoja());
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
            response.setDescricao(loja.getEnderecos().getFirst().getDescricao());
        }

        if (loja.getPessoasFisicas().size() > 0){
            response.setNomePessoaFisica(loja.getPessoasFisicas().getFirst().getNomePessoaFisica());
            response.setSobrenomePessoaFisica(loja.getPessoasFisicas().getFirst().getSobrenomePessoaFisica());
            response.setCpfPessoaFisica(loja.getPessoasFisicas().getFirst().getCpfPessoaFisica());
            response.setTelefonePessoaFisica(loja.getPessoasFisicas().getFirst().getTelefonePessoaFisica());
            response.setEmailPessoaFisica(loja.getPessoasFisicas().getFirst().getEmailPessoaFisica());
        }

        return response;

    }
}
