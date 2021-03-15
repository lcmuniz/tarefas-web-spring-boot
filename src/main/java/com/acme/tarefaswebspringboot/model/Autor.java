package com.acme.tarefaswebspringboot.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Autor {
    Long id;

    @NotBlank(message = "Nome é obrigatório")
    String nome;
}
