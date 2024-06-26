package com.ibmec.backend.malldelivery.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LojaAtivacaoRequest {
    @NotNull(message = "Habilitação não fornecida")
    private Boolean enabled;

    @NotBlank(message = "Nome de usuário para ativação não fornecido")
    private String userNameAtivacao;
}
