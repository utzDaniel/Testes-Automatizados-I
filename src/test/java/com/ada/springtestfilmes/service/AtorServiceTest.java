package com.ada.springtestfilmes.service;

import com.ada.springtestfilmes.domain.Ator;
import com.ada.springtestfilmes.exceptions.AtorNaoEncontradoException;
import com.ada.springtestfilmes.repository.AtorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AtorServiceTest {

    @Autowired
    private AtorService atorService;

    @Test
    @DisplayName("Deve adicionar um ator corretamente")
    public void adicionaAtor() {
        Ator ator = Ator.builder()
                .nome("ator0")
                .build();

        Ator retornoAtor = atorService.adicionaAtor(ator);
        Assertions.assertEquals(ator.getNome(), retornoAtor.getNome());
    }

    @Test
    @DisplayName("Deve ser possível buscar um ator por id existente")
    public void buscaPorId() {
        Ator ator = Ator.builder()
                .nome("atorBusca")
                .build();

        Ator addAtor = atorService.adicionaAtor(ator);
        Ator retornoAtor = atorService.buscaPorId(addAtor.getId());
        Assertions.assertEquals(ator.getNome(), retornoAtor.getNome());

    }

    @Test
    @DisplayName("Deve ser possível buscar um ator por id nao existente")
    public void testeBuscaAtorNaoExistente() {
        Assertions.assertThrows(AtorNaoEncontradoException.class, () -> atorService.buscaPorId(9999L));
    }

    @Test
    @DisplayName("Deve lista ator corretamente")
    public void listarAtores() {
        Ator ator = Ator.builder()
                .nome("atorList")
                .build();

        int size = atorService.listarAtores().size();
        atorService.adicionaAtor(ator);

        List<Ator> atores = atorService.listarAtores();

        Assertions.assertEquals(size+1, atores.size());
    }

    @Test
    @DisplayName("Deve ser possível deletar um ator por id existente")
    public void removeAtor() {
        Ator ator = Ator.builder()
                .nome("atorRemove")
                .build();

        Ator addAtor = atorService.adicionaAtor(ator);
        int size = atorService.listarAtores().size();

        List<Ator> atores = atorService.listarAtores();
        Ator retornoAtor = atorService.buscaPorId(addAtor.getId());
        atores.remove(retornoAtor);

        Assertions.assertEquals(size-1, atores.size());
        Assertions.assertFalse(atores.contains(retornoAtor));
    }

    @Test
    @DisplayName("Deve ser possível atualizar um ator existente")
    public void atualizaAtor() {
        Ator ator = Ator.builder()
                .nome("atorAt")
                .build();

        Ator addAtor = atorService.adicionaAtor(ator);

        Ator atorAtualiza = Ator.builder()
                .id(addAtor.getId())
                .nome("atorAtualiza")
                .build();

        Ator retornoAtorAtualizado = atorService.atualizaAtor(atorAtualiza);

        Assertions.assertEquals(atorAtualiza.getId(), retornoAtorAtualizado.getId());
        Assertions.assertEquals(atorAtualiza.getNome(), retornoAtorAtualizado.getNome());
    }
}