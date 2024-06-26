package com.ibmec.backend.malldelivery.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LojaRequest {
    @NotBlank(message = "O campo nome é obrigatório")
    private String nomeLoja;

    @NotBlank(message = "O campo email é obrigatório")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "O campo email deve ser um email válido")
    private String emailLoja;

    @NotBlank(message = "O campo telefone é obrigatório")
    @Pattern(regexp = "^\\([0-9]{2}\\)[0-9]{5}-[0-9]{4}$", message = "O campo telefone deve seguir o padrão (99)99999-9999")
    private String telefoneLoja;

    @NotBlank(message = "O campo cnpj é obrigatório")
    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}-\\d{2}$", message = "O campo cnpj deve seguir o padrão 99.999.999/9999-99")
    private String cnpj;

    @NotBlank(message = "O campo banner é obrigatório")
    private String bannerLoja;

    @NotBlank(message = "O campo perfil é obrigatório")
    private String perfilLoja;

    private String urlFacebook;

    private String urlInstagram;

    private String urlTwitter;

    private int numMaxProduto = 10;

    private int abaProdAdd = 3;

    @NotBlank(message = "O campo nome do banco é obrigatório")
    private String nomeBanco;

    @NotBlank(message = "O campo agência é obrigatório")
    @Pattern(regexp = "^[0-9]{4}$", message = "O campo agência deve ter 4 dígitos")
    private String agencia;

    @NotBlank(message = "O campo conta é obrigatório")
    @Pattern(regexp = "^[0-9]{5}-[0-9]$", message = "O campo conta deve seguir o padrão 99999-9")
    private String conta;

    @NotBlank(message = "O campo código do banco é obrigatório")
    @Pattern(regexp = "^[0-9]{3}$", message = "O campo código do banco deve ter 3 dígitos")
    private String codigoBanco;

    @NotBlank(message = "O campo tipo de conta é obrigatório")
    private String tipoConta;

    @NotBlank(message = "O campo logradouro é obrigatório")
    private String logradouro;

    @NotBlank(message = "O campo bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "O campo cidade é obrigatório")
    private String cidade;

    @NotBlank(message = "O campo cep é obrigatório")
    @Pattern(regexp = "^[0-9]{5}-[0-9]{3}$", message = "O campo cep deve seguir o padrão 99999-999")
    private String cep;

    @NotBlank(message = "O campo país é obrigatório")
    private String pais;

    @NotBlank(message = "O campo estado é obrigatório")
    private String estado;

    @NotBlank(message = "O campo complemento é obrigatório")
    private String complemento;

    private String descricao;

    @NotBlank(message = "O campo nome da pessoa física é obrigatório")
    private String nomePessoaFisica;

    @NotBlank(message = "O campo sobrenome da pessoa física é obrigatório")
    private String sobrenomePessoaFisica;

    @NotBlank(message = "O campo email da pessoa física é obrigatório")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "O campo email da pessoa física deve ser um email válido")
    private String emailPessoaFisica;

    @NotBlank(message = "O campo telefone da pessoa física é obrigatório")
    @Pattern(regexp = "^\\([0-9]{2}\\)[0-9]{5}-[0-9]{4}$", message = "O campo telefone da pessoa física deve seguir o padrão (99)99999-9999")
    private String telefonePessoaFisica;

    @NotBlank(message = "O campo cpf da pessoa física é obrigatório")
    @Pattern(regexp = "^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}$", message = "O campo cpf da pessoa física deve seguir o padrão 999.999.999-99")
    private String cpfPessoaFisica;
}
