package com.ada.springtestfilmes.repository;

import com.ada.springtestfilmes.domain.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
    List<Filme> findByNome(String nome);
    List<Filme> findByGeneros(String genero);
    List<Filme> findByAno(Integer ano);
    List<Filme> findByAtores(String ator);
}
