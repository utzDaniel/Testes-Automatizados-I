package com.ada.springtestfilmes.service;

import com.ada.springtestfilmes.domain.Ator;
import com.ada.springtestfilmes.domain.Filme;
import com.ada.springtestfilmes.domain.Genero;
import com.ada.springtestfilmes.exceptions.FilmeNaoEncontradoException;
import com.ada.springtestfilmes.repository.FilmeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FilmeService {

    private final FilmeRepository filmeRepository;

    public Filme adicionaFilme(Filme filme) {
        return filmeRepository.save(filme);
    }

    public List<Filme> listarFilmes() {
        return filmeRepository.findAll();
    }

    public Filme buscaPorId(Long id) throws FilmeNaoEncontradoException {
        return filmeRepository.findById(id)
                .orElseThrow(FilmeNaoEncontradoException::new);
    }


    public Filme removeFilme(Long id) {
        Filme filme = this.buscaPorId(id);
        filmeRepository.delete(filme);
        return filme;
    }

    public Filme atualizaFilme(Filme novoFilme) {
        Filme filmeBanco = this.buscaPorId(novoFilme.getId());

        filmeBanco.setNome(novoFilme.getNome());
        filmeBanco.setAno(novoFilme.getAno());
        filmeBanco.setAtores(novoFilme.getAtores());
        filmeBanco.setGenero(novoFilme.getGenero());

        return filmeBanco;
    }

    public List<Filme> buscaPorNome(String nome) {
        return filmeRepository.findByNome(nome);
    }

    public List<Filme> buscaPorGenero(Genero genero) {
        return filmeRepository.findByGenero(genero);
    }

    public List<Filme> buscaPorAno(Integer ano) {
        return filmeRepository.findByAno(ano);
    }

    public List<Filme> buscaPorAtor(Ator ator) {
        return filmeRepository.findByAtores(ator);
    }

}
