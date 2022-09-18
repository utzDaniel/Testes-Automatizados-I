package com.ada.springtestfilmes.service;

import com.ada.springtestfilmes.domain.Ator;
import com.ada.springtestfilmes.domain.Filme;
import com.ada.springtestfilmes.domain.Genero;
import com.ada.springtestfilmes.exceptions.FilmeNaoEncontradoException;
import com.ada.springtestfilmes.repository.FilmeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class FilmesServiceTest {

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private AtorService atorService;
    @Autowired
    private FilmeRepository filmeRepository;

    @BeforeEach
    public void limpaBanco() {
        filmeRepository.deleteAll();
        atorService.adicionaAtor(Ator.builder()
                .nome("ator0")
                .build());
        atorService.adicionaAtor(Ator.builder()
                .nome("ator1")
                .build());
        atorService.adicionaAtor(Ator.builder()
                .nome("ator2")
                .build());
    }

    @Test
    @DisplayName("Deve adicionar um filme corretamente")
    public void testaInsereNoBanco() {
        List<Ator> listAtor = new ArrayList<>();
        List<Ator> listarAtores = atorService.listarAtores();
        listAtor.add(listarAtores.get(0));
        listAtor.add(listarAtores.get(1));
        listAtor.add(listarAtores.get(2));

        Filme filme = Filme.builder()
                .nome("Teste").ano(2018)
                .atores(listAtor)
                .genero(Genero.ACAO)
                .build();

        Filme retornoFilme = filmeService.adicionaFilme(filme);

        Assertions.assertEquals(filme.getNome(), retornoFilme.getNome());
        Assertions.assertEquals(filme.getAno(), retornoFilme.getAno());
        Assertions.assertEquals(filme.getGenero(), retornoFilme.getGenero());
        Assertions.assertEquals(filme.getAtores().get(0), retornoFilme.getAtores().get(0));
        Assertions.assertEquals(filme.getAtores().get(1), retornoFilme.getAtores().get(1));
        Assertions.assertEquals(filme.getAtores().get(2), retornoFilme.getAtores().get(2));
    }

    @Test
    @DisplayName("Deve lista filme corretamente")
    public void testaListaFilmes() {
        List<Ator> listAtor = new ArrayList<>();
        List<Ator> listarAtores = atorService.listarAtores();
        listAtor.add(listarAtores.get(0));
        listAtor.add(listarAtores.get(1));
        listAtor.add(listarAtores.get(2));

        Filme filme = Filme.builder()
                .nome("Teste").ano(2018)
                .atores(listAtor)
                .genero(Genero.ACAO)
                .build();

        int size =  filmeService.listarFilmes().size();
        filmeService.adicionaFilme(filme);

        Assertions.assertEquals(size+1, filmeService.listarFilmes().size());
    }

    @Test
    @DisplayName("Deve ser possível buscar um filme por id existente")
    public void testBuscaPorId() {
        List<Ator> listAtor = new ArrayList<>();
        List<Ator> listarAtores = atorService.listarAtores();
        listAtor.add(listarAtores.get(0));
        listAtor.add(listarAtores.get(1));
        listAtor.add(listarAtores.get(2));

        Filme filme = Filme.builder()
                .nome("Teste").ano(2018)
                .atores(listAtor)
                .genero(Genero.ACAO)
                .build();

        Filme addFilme = filmeService.adicionaFilme(filme);
        Filme retornoFilme = filmeService.buscaPorId(addFilme.getId());

        Assertions.assertEquals(filme.getNome(), retornoFilme.getNome());
        Assertions.assertEquals(filme.getAno(), retornoFilme.getAno());
        Assertions.assertEquals(filme.getGenero(), retornoFilme.getGenero());
    }

    @Test
    @DisplayName("Deve ser possível deletar um filme por id existente")
    public void testDeletePorId() {
        List<Ator> listAtor = new ArrayList<>();
        List<Ator> listarAtores = atorService.listarAtores();
        listAtor.add(listarAtores.get(0));
        listAtor.add(listarAtores.get(1));
        listAtor.add(listarAtores.get(2));

        Filme filme = Filme.builder()
                .nome("Teste").ano(2018)
                .atores(listAtor)
                .genero(Genero.ACAO)
                .build();

        Filme addFilme = filmeService.adicionaFilme(filme);

        Filme retornoFilme = filmeService.buscaPorId(addFilme.getId());
        System.out.println(retornoFilme.getId());

        int size = filmeService.listarFilmes().size();

        filmeService.removeFilme(retornoFilme.getId());

        Assertions.assertEquals(size-1, filmeService.listarFilmes().size());
        Assertions.assertFalse(filmeService.listarFilmes().contains(retornoFilme));
    }

    @Test
    @DisplayName("Deve ser possível buscar um filme por id nao existente")
    public void testeBuscaFilmeNaoExistente() {
        Assertions.assertThrows(FilmeNaoEncontradoException.class, () -> filmeService.buscaPorId(9999L));
    }

    @Test
    @DisplayName("Deve ser possível atualizar um filme existente")
    public void testAtualizarFilme() {
        List<Ator> listAtor = new ArrayList<>();
        List<Ator> listarAtores = atorService.listarAtores();
        listAtor.add(listarAtores.get(0));
        listAtor.add(listarAtores.get(1));
        listAtor.add(listarAtores.get(2));

        Filme filme = Filme.builder()
                .nome("Teste").ano(2018)
                .atores(listAtor)
                .genero(Genero.ACAO)
                .build();

        Filme addFilme = filmeService.adicionaFilme(filme);

        List<Ator> listAtorAtualizar = new ArrayList<>();
        listAtorAtualizar.add(listarAtores.get(2));
        listAtorAtualizar.add(listarAtores.get(0));
        listAtorAtualizar.add(listarAtores.get(1));

        Filme filmeAtualizar = Filme.builder()
                .id(addFilme.getId())
                .nome("TesteAtualizar").ano(2020)
                .atores(listAtorAtualizar)
                .genero(Genero.DRAMA)
                .build();

        Filme retornoFilmeAtualizado = filmeService.atualizaFilme(filmeAtualizar);

        Assertions.assertEquals(filmeAtualizar.getId(), retornoFilmeAtualizado.getId());
        Assertions.assertEquals(filmeAtualizar.getNome(), retornoFilmeAtualizado.getNome());
        Assertions.assertEquals(filmeAtualizar.getAno(), retornoFilmeAtualizado.getAno());
        Assertions.assertEquals(filmeAtualizar.getGenero(), retornoFilmeAtualizado.getGenero());
        Assertions.assertEquals(filmeAtualizar.getAtores().get(0), retornoFilmeAtualizado.getAtores().get(0));
        Assertions.assertEquals(filmeAtualizar.getAtores().get(1), retornoFilmeAtualizado.getAtores().get(1));
        Assertions.assertEquals(filmeAtualizar.getAtores().get(2), retornoFilmeAtualizado.getAtores().get(2));
    }

    @Test
    @DisplayName("Deve ser possível buscar um filme existente por nome")
    public void testBuscarPorNomeFilme() {
        List<Ator> listAtor = new ArrayList<>();
        List<Ator> listarAtores = atorService.listarAtores();
        listAtor.add(listarAtores.get(0));
        listAtor.add(listarAtores.get(1));
        listAtor.add(listarAtores.get(2));

        Filme filme = Filme.builder()
                .nome("Teste").ano(2018)
                .atores(listAtor)
                .genero(Genero.ACAO)
                .build();

        filmeService.adicionaFilme(filme);

        List<Filme> filmes = filmeService.buscaPorNome("Teste");

        Assertions.assertEquals(1, filmes.size());
        Assertions.assertFalse(filmes.contains(filme));
        Assertions.assertEquals(filme.getNome(), filmes.get(0).getNome());
        Assertions.assertEquals(filme.getAno(), filmes.get(0).getAno());
        Assertions.assertEquals(filme.getGenero(), filmes.get(0).getGenero());
    }

    @Test
    @DisplayName("Deve ser possível buscar um filme existente por genero")
    public void testBuscarPorGeneroFilme() {
        List<Ator> listAtor = new ArrayList<>();
        List<Ator> listarAtores = atorService.listarAtores();
        listAtor.add(listarAtores.get(0));
        listAtor.add(listarAtores.get(1));
        listAtor.add(listarAtores.get(2));

        Filme filme = Filme.builder()
                .nome("Teste").ano(2018)
                .atores(listAtor)
                .genero(Genero.ACAO)
                .build();

        filmeService.adicionaFilme(filme);

        List<Filme> filmes = filmeService.buscaPorGenero(Genero.ACAO);

        Assertions.assertEquals(1, filmes.size());
        Assertions.assertFalse(filmes.contains(filme));
        Assertions.assertEquals(filme.getNome(), filmes.get(0).getNome());
        Assertions.assertEquals(filme.getAno(), filmes.get(0).getAno());
        Assertions.assertEquals(filme.getGenero(), filmes.get(0).getGenero());
    }

    @Test
    @DisplayName("Deve ser possível buscar um filme existente por ano")
    public void testBuscarPorAnoFilme() {
        List<Ator> listAtor = new ArrayList<>();
        List<Ator> listarAtores = atorService.listarAtores();
        listAtor.add(listarAtores.get(0));
        listAtor.add(listarAtores.get(1));
        listAtor.add(listarAtores.get(2));

        Filme filme = Filme.builder()
                .nome("Teste").ano(2018)
                .atores(listAtor)
                .genero(Genero.ACAO)
                .build();

        filmeService.adicionaFilme(filme);

        List<Filme> filmes = filmeService.buscaPorAno(2018);

        Assertions.assertEquals(1, filmes.size());
        Assertions.assertFalse(filmes.contains(filme));
        Assertions.assertEquals(filme.getNome(), filmes.get(0).getNome());
        Assertions.assertEquals(filme.getAno(), filmes.get(0).getAno());
        Assertions.assertEquals(filme.getGenero(), filmes.get(0).getGenero());
    }

    @Test
    @DisplayName("Deve ser possível buscar um filme existente por ator")
    public void testBuscarPorAtorFilme() {
        List<Ator> listAtor = new ArrayList<>();
        List<Ator> listarAtores = atorService.listarAtores();
        listAtor.add(listarAtores.get(0));
        listAtor.add(listarAtores.get(1));
        listAtor.add(listarAtores.get(2));

        Filme filme = Filme.builder()
                .nome("Teste").ano(2018)
                .atores(listAtor)
                .genero(Genero.ACAO)
                .build();

        filmeService.adicionaFilme(filme);

        List<Filme> filmes = filmeService.buscaPorAtor(atorService.buscaPorId(listarAtores.get(1).getId()));

        Assertions.assertEquals(1, filmes.size());
        Assertions.assertFalse(filmes.contains(filme));
        Assertions.assertEquals(filme.getNome(), filmes.get(0).getNome());
        Assertions.assertEquals(filme.getAno(), filmes.get(0).getAno());
        Assertions.assertEquals(filme.getGenero(), filmes.get(0).getGenero());
    }
}
