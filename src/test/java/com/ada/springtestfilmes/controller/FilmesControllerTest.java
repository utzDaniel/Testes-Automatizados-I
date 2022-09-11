package com.ada.springtestfilmes.controller;


import com.ada.springtestfilmes.domain.Filme;
import com.ada.springtestfilmes.repository.FilmeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilmesControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private FilmeRepository filmeRepository;

    @BeforeEach
    public void limpaBanco() {
        filmeRepository.deleteAll();
    }

    @Test
    public void testInserePorApi() {

        Filme filme = Filme.builder()
                .nome("Teste").ano(2018)
                .atores(null)
                .generos(null)
                .build();

        ResponseEntity<Filme> filmeResposta = this.testRestTemplate
                .postForEntity(String.format("http://localhost:%s/filmes",port),
                        filme, Filme.class);

        Assertions.assertEquals(HttpStatus.CREATED, filmeResposta.getStatusCode());
        Assertions.assertEquals(filme, filmeResposta.getBody());
    }
    @Test
    public void testBuscaPorApi() {
        Filme filme = Filme.builder()
                .nome("Teste").ano(2018)
                .atores(null)
                .generos(null)
                .build();

        this.testRestTemplate
                .postForEntity(String.format("http://localhost:%s/filmes",port),
                        filme, Filme.class);

        Long id = 1L;

        ResponseEntity<Filme> filmeResposta = this.testRestTemplate
                .getForEntity(String.format("http://localhost:%s/filmes/%s",port,id),
                        Filme.class);

        Assertions.assertEquals(HttpStatus.OK, filmeResposta.getStatusCode());
        Assertions.assertEquals(filme, filmeResposta.getBody());
    }

    @Test
    public void testListAllFilmePorApi() {
        Filme filme = Filme.builder()
                .nome("Teste").ano(2018)
                .atores(null)
                .generos(null)
                .build();

        this.testRestTemplate
                .postForEntity(String.format("http://localhost:%s/filmes",port),
                        filme, Filme.class);

        ResponseEntity<List> filmeResposta = this.testRestTemplate
                .getForEntity(String.format("http://localhost:%s/filmes",port)
                        ,List.class);
        List<Filme> filmeList = filmeResposta.getBody();

        Assertions.assertEquals(HttpStatus.OK, filmeResposta.getStatusCode());
        Assertions.assertEquals(1, filmeList.size());
    }

    @Test
    public void testDeletarPorApi() {
        Filme filme = Filme.builder()
                .nome("Teste").ano(2018)
                .atores(null)
                .generos(null)
                .build();

        ResponseEntity<Filme> filmeResposta = this.testRestTemplate
                .postForEntity(String.format("http://localhost:%s/filmes",port),
                        filme, Filme.class);

        Long id = 1L;

        this.testRestTemplate
                .delete(String.format("http://localhost:%s/filmes/%s",port,id));

        try{
            filmeResposta = this.testRestTemplate
                    .getForEntity(String.format("http://localhost:%s/filmes/%s",port,id),
                            Filme.class);
        }catch (IllegalArgumentException e){
            Assertions.assertTrue(true);
        }
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, filmeResposta.getStatusCode());

    }
}
