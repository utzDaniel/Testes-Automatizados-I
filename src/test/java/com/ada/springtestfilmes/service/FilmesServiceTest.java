package com.ada.springtestfilmes.service;

import com.ada.springtestfilmes.domain.Filme;
import com.ada.springtestfilmes.exceptions.FilmeNaoEncontradoException;
import com.ada.springtestfilmes.repository.FilmeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class FilmesServiceTest {

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private FilmeRepository filmeRepository;

    @BeforeEach
    public void limpaBanco() {
        filmeRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve adicionar um filme corretamente")
    public void testaInsereNoBanco() {
        Filme filme = Filme.builder()
                .nome("Teste").ano(2018)
                .atores(null)
                .generos(null)
                .build();

        Filme retornoFilme = filmeService.adicionaFilme(filme);
        Assertions.assertEquals(retornoFilme, filme);

    }

    @Test
    @DisplayName("Deve ser possível buscar um filme por id existente")
    public void testBuscaPorId() {
        Filme filme = Filme.builder()
                .nome("Teste").ano(2018)
                .atores(null)
                .generos(null)
                .build();

        filmeService.adicionaFilme(filme);
        Filme retornoFilme = filmeService.buscaPorId(1L);

        Assertions.assertNotNull(filme);
        Assertions.assertEquals(retornoFilme, filme);
    }

    @Test
    @DisplayName("Deve ser possível deletar um médico por id existente")
    public void testDeletePorId() {
        Filme filme = Filme.builder()
                .nome("Teste").ano(2018)
                .atores(null)
                .generos(null)
                .build();

        filmeService.adicionaFilme(filme);

        List<Filme> filmes = filmeService.listarFilmes();
        Filme retornoFilme = filmeService.buscaPorId(1L);
        filmes.remove(retornoFilme);
        Assertions.assertEquals(0, filmes.size());
        Assertions.assertFalse(filmes.contains(retornoFilme));
    }

    @Test
    @DisplayName("Deve ser possível buscar um filme por id nao existente")
    public void testeBuscaFilmeNaoExistente() {
        Assertions.assertThrows(FilmeNaoEncontradoException.class, () -> filmeService.buscaPorId(9999L));
    }

}
