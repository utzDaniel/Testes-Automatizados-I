package com.ada.springtestfilmes.controller;

import com.ada.springtestfilmes.domain.Ator;
import com.ada.springtestfilmes.domain.Filme;
import com.ada.springtestfilmes.service.AtorService;
import com.ada.springtestfilmes.service.FilmeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class ViewController {
    private FilmeService filmeService;
    private AtorService atorService;

    @GetMapping("/home")
    public String home(Model model) {
        List<Filme> lista = this.filmeService.listarFilmes();
        model.addAttribute("filmes", lista);
        return "home";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Filme filme = new Filme();
        filme.setAtores(List.of
                (new Ator(), new Ator(), new Ator()));
        model.addAttribute("filme", filme);
        model.addAttribute("atores", atorService.listarAtores());
        return "create";
    }

    @PostMapping("/save")
    public String save(@Valid Filme filme, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create";
        }
        filmeService.adicionaFilme(filme);
        return "redirect:/home";
    }

    @GetMapping("/create-ator")
    public String createAtor(Model model) {
        model.addAttribute("ator", new Ator());
        return "create-ator";
    }

    @PostMapping("/save-ator")
    public String saveAtor(@Valid Ator ator, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create-ator";
        }
        atorService.adicionaAtor(ator);
        return "redirect:/home";
    }

    @GetMapping("/remover/{id}")
    public String removerFilme(@PathVariable Long id) {
        filmeService.removeFilme(id);
        return "redirect:/home";
    }

    @GetMapping("/atualizar/{id}")
    public String atualizarFilme(@PathVariable Long id,Model model) {
        Filme filme = filmeService.buscaPorId(id);
        model.addAttribute("filme", filme);
        model.addAttribute("atores", atorService.listarAtores());
        return "atualizar";
    }

    @PostMapping("/update")
    public String update(@Valid Filme filme, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "atualizar/"+filme.getId();
        }
        filmeService.atualizaFilme(filme);
        return "redirect:/home";
    }
}
