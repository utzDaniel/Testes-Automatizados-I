package com.ada.springtestfilmes.controller;


import com.ada.springtestfilmes.domain.Ator;
import com.ada.springtestfilmes.domain.Filme;
import com.ada.springtestfilmes.repository.AtorRepository;
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

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AtorControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AtorRepository atorRepository;

    @BeforeEach
    public void limpaBanco() {
        atorRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve adicionar um ator corretamente por api")
    public void testInserePorApi() {
        Ator ator = Ator.builder()
                .nome("ator0")
                .build();

        ResponseEntity<Ator> atorResposta = this.testRestTemplate
                .postForEntity(String.format("http://localhost:%s/ator",port),
                        ator, Ator.class);

        Assertions.assertEquals(HttpStatus.CREATED, atorResposta.getStatusCode());
        Assertions.assertEquals(ator.getNome(), atorResposta.getBody().getNome());
    }

    @Test
    @DisplayName("Deve ser possível buscar um ator por id existente por api")
    public void testBuscaPorApi() {
        Ator ator = Ator.builder()
                .nome("ator0")
                .build();

        ResponseEntity<Ator> addAtor = this.testRestTemplate
                .postForEntity(String.format("http://localhost:%s/ator",port),
                        ator, Ator.class);

        Long id = addAtor.getBody().getId();

        ResponseEntity<Ator> retornoAtor = this.testRestTemplate
                .getForEntity(String.format("http://localhost:%s/ator/%s",port,id),
                        Ator.class);
        Assertions.assertEquals(HttpStatus.OK, retornoAtor.getStatusCode());
        Assertions.assertEquals(ator.getNome(), retornoAtor.getBody().getNome());
    }

    @Test
    @DisplayName("Deve lista ator corretamente por api")
    public void testListFilmePorApi() {
        Ator ator = Ator.builder()
                .nome("ator0")
                .build();

        int size = this.testRestTemplate
                .getForEntity(String.format("http://localhost:%s/ator",port)
                        ,List.class).getBody().size();

                this.testRestTemplate
                .postForEntity(String.format("http://localhost:%s/ator",port),
                        ator, Ator.class);

        ResponseEntity<List> listAtor = this.testRestTemplate
                .getForEntity(String.format("http://localhost:%s/ator",port)
                        ,List.class);

        Assertions.assertEquals(HttpStatus.OK, listAtor.getStatusCode());
        Assertions.assertEquals(size+1, listAtor.getBody().size());
    }

    @Test
    @DisplayName("Deve ser possível deletar um ator por id existente por api")
    public void testDeletarPorApi() {
        Ator ator = Ator.builder()
                .nome("ator0")
                .build();

        ResponseEntity<Ator> atorResposta = this.testRestTemplate
                .postForEntity(String.format("http://localhost:%s/ator",port),
                        ator, Ator.class);

        int size = this.testRestTemplate
                .getForEntity(String.format("http://localhost:%s/ator",port)
                        ,List.class).getBody().size();

        Long id = atorResposta.getBody().getId();

        this.testRestTemplate
                .delete(String.format("http://localhost:%s/ator/%s",port,id));

        Assertions.assertEquals(size-1, this.testRestTemplate
                .getForEntity(String.format("http://localhost:%s/ator",port)
                        ,List.class).getBody().size());
        try{
            atorResposta = this.testRestTemplate
                    .getForEntity(String.format("http://localhost:%s/ator/%s",port,id),
                            Ator.class);
        }catch (IllegalArgumentException e){
            Assertions.assertTrue(true);
        }
        Assertions.assertEquals(HttpStatus.NOT_FOUND, atorResposta.getStatusCode());
    }

    @Test
    @DisplayName("Deve ser possível atualizar um ator existente por api")
    public void testAtualizarPorApi() {
        Ator ator = Ator.builder()
                .nome("ator0")
                .build();

        ResponseEntity<Ator> addAtor = this.testRestTemplate
                .postForEntity(String.format("http://localhost:%s/ator",port),
                        ator, Ator.class);

        Ator atorAtualizar = Ator.builder()
                .id(addAtor.getBody().getId())
                .nome("atorAtualizado")
                .build();

        HttpEntity<Ator> atorHttpEntity = new HttpEntity<>(atorAtualizar);
        ResponseEntity<Ator> atorRespostaAtualizado =
                this.testRestTemplate.exchange(String.format("http://localhost:%s/ator",port),
                        HttpMethod.PUT,atorHttpEntity,Ator.class);

        Assertions.assertEquals(atorAtualizar.getId(), atorRespostaAtualizado.getBody().getId());
        Assertions.assertEquals(atorAtualizar.getNome(), atorRespostaAtualizado.getBody().getNome());
    }
}
