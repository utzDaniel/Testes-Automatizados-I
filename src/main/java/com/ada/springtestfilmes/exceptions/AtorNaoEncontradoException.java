package com.ada.springtestfilmes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AtorNaoEncontradoException extends ResponseStatusException {

    public AtorNaoEncontradoException() {
        super(HttpStatus.NOT_FOUND);
    }
}
