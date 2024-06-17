package com.ibmec.backend.malldelivery.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequest {

    @NotEmpty(message="Usuario nao pode ser vazio")
    private String username;

    @NotEmpty(message="Senha nao pode ser vazio")
    private String password;

    @NotNull(message = "O campo perfil não pode ser vazio")
    private int profile_id;
}
