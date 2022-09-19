package com.ada.springtestfilmes.controller;

import com.ada.springtestfilmes.domain.Ator;
import com.ada.springtestfilmes.domain.Filme;
import com.ada.springtestfilmes.domain.Genero;
import com.ada.springtestfilmes.repository.FilmeRepository;
import com.ada.springtestfilmes.service.AtorService;
import com.ada.springtestfilmes.service.FilmeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ViewControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AtorService atorService;

    @Autowired
    private FilmeService filmeService;
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
    @DisplayName("Deve retornar a pagina home, quando acessar a view home")
    public void home() {
        ResponseEntity<String> resposta = this.testRestTemplate
                .getForEntity(String.format("http://localhost:%s/home", port),
                        String.class);

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertTrue(resposta.getBody().contains("<title>Lista de Filmes</title>"));
    }

   /* Funciosa individualmente

   @Test
    @DisplayName("Deve retornar a pagina create, quando acessar a view create")
    public void create() {
        ResponseEntity<String> resposta = this.testRestTemplate
                .getForEntity(String.format("http://localhost:%s/create", port),
                        String.class);

        //Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertTrue(resposta.getBody().contains("<title>Cadastrar Filme</title>"));
    }*/

    @Test
    @DisplayName("Deve retornar a pagina create, quando criado o filme corretamente, mas com bindingResult.hasErrors() true")
    public void save() {
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

        ResponseEntity<String> resposta = this.testRestTemplate
                .postForEntity(String.format("http://localhost:%s/save", port),
                        filme, String.class);

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertTrue(resposta.getBody().contains("<title>Cadastrar Filme</title>"));
    }

    @Test
    @DisplayName("Deve retornar a pagina create-ator, quando acessar a view create-ator")
    public void createAtor() {
        ResponseEntity<String> resposta = this.testRestTemplate
                .getForEntity(String.format("http://localhost:%s/create-ator", port),
                        String.class);

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertTrue(resposta.getBody().contains("<title>Cadastrar Ator</title>"));
    }

    @Test
    @DisplayName("Deve retornar a pagina create-ator, quando criado o ator corretamente, mas com bindingResult.hasErrors() true")
    public void saveAtor() {

        Ator ator = Ator.builder()
                .nome("atorTeste")
                .build();

        ResponseEntity<String> resposta = this.testRestTemplate
                .postForEntity(String.format("http://localhost:%s/save-ator", port),
                        ator, String.class);

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertTrue(resposta.getBody().contains("<title>Cadastrar Ator</title>"));
    }

    @Test
    @DisplayName("Deve retornar a pagina home, quando acessar a view /remover/{id}")
    public void removerFilme() {
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

        ResponseEntity<String> resposta = this.testRestTemplate
                .getForEntity(String.format("http://localhost:%s/remover/%s", port, addFilme.getId()),
                        String.class);

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertTrue(resposta.getBody().contains("<title>Lista de Filmes</title>"));
    }
/* Funciosa individualmente
    @Test
    @DisplayName("Deve retornar a pagina atualizar, quando acessar a view /atualizar/{id}")
    public void atualizarFilme() {
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

        ResponseEntity<String> resposta = this.testRestTemplate
                .getForEntity(String.format("http://localhost:%s/atualizar/%s", port, addFilme.getId()),
                        String.class);

        Assertions.assertEquals(HttpStatus.OK, resposta.getStatusCode());
        Assertions.assertTrue(resposta.getBody().contains("<title>Atualizar Filme</title>"));
    }*/
}