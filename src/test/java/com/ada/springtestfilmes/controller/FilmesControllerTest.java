package com.ada.springtestfilmes.controller;


import com.ada.springtestfilmes.domain.Ator;
import com.ada.springtestfilmes.domain.Filme;
import com.ada.springtestfilmes.domain.Genero;
import com.ada.springtestfilmes.repository.FilmeRepository;
import com.ada.springtestfilmes.service.AtorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilmesControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

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
    @DisplayName("Deve adicionar um filme corretamente por api")
    public void testInserePorApi() {
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

        ResponseEntity<Filme> filmeResposta = this.testRestTemplate
                .postForEntity(String.format("http://localhost:%s/filmes",port),
                        filme, Filme.class);

        Assertions.assertEquals(HttpStatus.CREATED, filmeResposta.getStatusCode());
        Assertions.assertEquals(filme.getNome(), filmeResposta.getBody().getNome());
        Assertions.assertEquals(filme.getAno(), filmeResposta.getBody().getAno());
        Assertions.assertEquals(filme.getGenero(), filmeResposta.getBody().getGenero());
        Assertions.assertEquals(filme.getAtores().get(0), filmeResposta.getBody().getAtores().get(0));
        Assertions.assertEquals(filme.getAtores().get(1), filmeResposta.getBody().getAtores().get(1));
        Assertions.assertEquals(filme.getAtores().get(2), filmeResposta.getBody().getAtores().get(2));
    }
    @Test
    @DisplayName("Deve ser possível buscar um filme por id existente por api")
    public void testBuscaPorApi() {
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

        ResponseEntity<Filme> addFilme = this.testRestTemplate
                .postForEntity(String.format("http://localhost:%s/filmes",port),
                        filme, Filme.class);

        Long id = addFilme.getBody().getId();

        ResponseEntity<Filme> filmeResposta = this.testRestTemplate
                .getForEntity(String.format("http://localhost:%s/filmes/%s",port,id),
                        Filme.class);
        Assertions.assertEquals(HttpStatus.OK, filmeResposta.getStatusCode());
        Assertions.assertEquals(filme.getNome(), filmeResposta.getBody().getNome());
        Assertions.assertEquals(filme.getAno(), filmeResposta.getBody().getAno());
        Assertions.assertEquals(filme.getGenero(), filmeResposta.getBody().getGenero());
        Assertions.assertEquals(filme.getAtores().get(0), filmeResposta.getBody().getAtores().get(0));
        Assertions.assertEquals(filme.getAtores().get(1), filmeResposta.getBody().getAtores().get(1));
        Assertions.assertEquals(filme.getAtores().get(2), filmeResposta.getBody().getAtores().get(2));
    }

    @Test
    @DisplayName("Deve lista filme corretamente por api")
    public void testListFilmePorApi() {
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

        int size = this.testRestTemplate
                .getForEntity(String.format("http://localhost:%s/filmes",port)
                        ,List.class).getBody().size();

                this.testRestTemplate
                .postForEntity(String.format("http://localhost:%s/filmes",port),
                        filme, Filme.class);

        ResponseEntity<List> filmeResposta = this.testRestTemplate
                .getForEntity(String.format("http://localhost:%s/filmes",port)
                        ,List.class);

        Assertions.assertEquals(HttpStatus.OK, filmeResposta.getStatusCode());
        Assertions.assertEquals(size+1, filmeResposta.getBody().size());
    }

    @Test
    @DisplayName("Deve ser possível deletar um filme por id existente por api")
    public void testDeletarPorApi() {
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

        ResponseEntity<Filme> filmeResposta = this.testRestTemplate
                .postForEntity(String.format("http://localhost:%s/filmes",port),
                        filme, Filme.class);

        int size = this.testRestTemplate
                .getForEntity(String.format("http://localhost:%s/filmes",port)
                        ,List.class).getBody().size();

        Long id = filmeResposta.getBody().getId();

        this.testRestTemplate
                .delete(String.format("http://localhost:%s/filmes/%s",port,id));

        Assertions.assertEquals(size-1, this.testRestTemplate
                .getForEntity(String.format("http://localhost:%s/filmes",port)
                        ,List.class).getBody().size());
        try{
            filmeResposta = this.testRestTemplate
                    .getForEntity(String.format("http://localhost:%s/filmes/%s",port,id),
                            Filme.class);
        }catch (IllegalArgumentException e){
            Assertions.assertTrue(true);
        }
        Assertions.assertEquals(HttpStatus.NOT_FOUND, filmeResposta.getStatusCode());
    }

    @Test
    @DisplayName("Deve ser possível atualizar um filme existente por api")
    public void testAtualizarPorApi() {
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

        ResponseEntity<Filme> addFilme = this.testRestTemplate
                .postForEntity(String.format("http://localhost:%s/filmes",port),
                        filme, Filme.class);


        List<Ator> listAtorAtualizar = new ArrayList<>();
        listAtorAtualizar.add(listarAtores.get(2));
        listAtorAtualizar.add(listarAtores.get(0));
        listAtorAtualizar.add(listarAtores.get(1));

        Filme filmeAtualizar = Filme.builder()
                .id(addFilme.getBody().getId())
                .nome("TesteAtualizar").ano(2020)
                .atores(listAtorAtualizar)
                .genero(Genero.DRAMA)
                .build();

        HttpEntity<Filme> filmeHttpEntity = new HttpEntity<>(filmeAtualizar);
        ResponseEntity<Filme> filmeRespostaAtualizado =
                this.testRestTemplate.exchange(String.format("http://localhost:%s/filmes",port),
                HttpMethod.PUT,filmeHttpEntity,Filme.class);


        Assertions.assertEquals(filmeAtualizar.getId(), filmeRespostaAtualizado.getBody().getId());
        Assertions.assertEquals(filmeAtualizar.getNome(), filmeRespostaAtualizado.getBody().getNome());
        Assertions.assertEquals(filmeAtualizar.getAno(), filmeRespostaAtualizado.getBody().getAno());
        Assertions.assertEquals(filmeAtualizar.getGenero(), filmeRespostaAtualizado.getBody().getGenero());
    }
}
