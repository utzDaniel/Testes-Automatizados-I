package com.ada.springtestfilmes.controller;

import com.ada.springtestfilmes.domain.Ator;
import com.ada.springtestfilmes.service.AtorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ator")
@AllArgsConstructor
public class AtorController {

    private AtorService atorService;

    @GetMapping
    public ResponseEntity<List<Ator>> listarAtores() {
        List<Ator> listaAtores = atorService.listarAtores();
        return new ResponseEntity<>(listaAtores, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Ator> adicionaFilme(@RequestBody @Valid Ator ator) {
        atorService.adicionaAtor(ator);
        return new ResponseEntity<>(ator, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Ator> atualizaAtor(@RequestBody Ator novoAtor) {
        Ator filme = atorService.atualizaAtor(novoAtor);
        return new ResponseEntity<>(filme, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ator> buscaFilmePorId(@PathVariable Long id) {
        Ator ator = atorService.buscaPorId(id);
        return new ResponseEntity<>(ator, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Ator> removerFilme(@PathVariable Long id) {
        Ator ator = atorService.removeAtor(id);
        return new ResponseEntity<>(ator, HttpStatus.OK);
    }

}
