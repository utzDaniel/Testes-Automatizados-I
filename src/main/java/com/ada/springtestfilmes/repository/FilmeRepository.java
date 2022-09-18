package com.ada.springtestfilmes.repository;

import com.ada.springtestfilmes.domain.Ator;
import com.ada.springtestfilmes.domain.Filme;
import com.ada.springtestfilmes.domain.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
    List<Filme> findByNome(String nome);
    List<Filme> findByGenero(Genero genero);
    List<Filme> findByAno(Integer ano);
    List<Filme> findByAtores(Ator ator);
}
