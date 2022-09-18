package com.ada.springtestfilmes.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Genero {

    ACAO("Ação"),
    AVENTURA("Aventura"),
    DRAMA("Drama "),
    COMEDIA("Comédia"),
    ROMANTICO("Romântico"),
    FICCAO("Ficção "),
    TERROR("Terror");

    private final String label;

}
