package com.ada.springtestfilmes.controller;

import com.ada.springtestfilmes.domain.Filme;
import com.ada.springtestfilmes.service.FilmeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class ViewController {
    private FilmeService filmeService;

    @GetMapping("/home")
    public String home(Model model) {
        List<Filme> lista = this.filmeService.listarFilmes();
        model.addAttribute("filmes", lista);
        return "home";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("filme", new Filme());
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

}
