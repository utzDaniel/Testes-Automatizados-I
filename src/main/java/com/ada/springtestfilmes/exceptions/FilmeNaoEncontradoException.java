package com.ada.springtestfilmes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FilmeNaoEncontradoException extends ResponseStatusException {

    public FilmeNaoEncontradoException() {
        super(HttpStatus.NOT_FOUND);
    }
}
