package com.ibmec.backend.malldelivery.errorHandler;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationErrorResponse {
    private String message = "Existem erros na sua requisição, por favor verifique";
    private List<Validation> validationsErrors = new ArrayList<>();
}
