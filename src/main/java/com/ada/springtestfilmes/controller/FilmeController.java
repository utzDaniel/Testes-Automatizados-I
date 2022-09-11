package com.ada.springtestfilmes.controller;

import com.ada.springtestfilmes.domain.Filme;
import com.ada.springtestfilmes.service.FilmeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filmes")
@AllArgsConstructor
public class FilmeController {

    private FilmeService filmeService;

    @GetMapping("/all")
    public ResponseEntity<List<Filme>> listarFilmes() {
        List<Filme> listaFilmes = filmeService.listarFilmes();
        return new ResponseEntity<>(listaFilmes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Filme> adicionaFilme(@RequestBody Filme filme) {
        filmeService.adicionaFilme(filme);
        return new ResponseEntity<>(filme, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscaFilmePorId(@PathVariable Long id) {
        Filme filme = filmeService.buscaPorId(id);
        return new ResponseEntity<>(filme, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Filme> removerFilme(@PathVariable Long id) {
        Filme filme = filmeService.removeFilme(id);
        return new ResponseEntity<>(filme, HttpStatus.OK);
    }


}
