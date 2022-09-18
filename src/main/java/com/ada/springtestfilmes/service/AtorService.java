package com.ada.springtestfilmes.service;

import com.ada.springtestfilmes.domain.Ator;
import com.ada.springtestfilmes.exceptions.AtorNaoEncontradoException;
import com.ada.springtestfilmes.repository.AtorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AtorService {

    private final AtorRepository atorRepository;

    public Ator adicionaAtor(Ator ator) {
        return atorRepository.save(ator);
    }

    public List<Ator> listarAtores() {
        return atorRepository.findAll();
    }

    public Ator buscaPorId(Long id) throws AtorNaoEncontradoException {
        return atorRepository.findById(id)
                .orElseThrow(AtorNaoEncontradoException::new);
    }

    public Ator removeAtor(Long id) {
        Ator ator = this.buscaPorId(id);
        atorRepository.delete(ator);
        return ator;
    }

    public Ator atualizaAtor(Ator novoAtor) {
        Ator atorBanco = this.buscaPorId(novoAtor.getId());
        atorBanco.setNome(novoAtor.getNome());
        return atorBanco;
    }
}
