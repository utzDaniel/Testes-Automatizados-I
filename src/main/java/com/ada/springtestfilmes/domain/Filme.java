package com.ada.springtestfilmes.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String generos;

    @NotNull
    @Min(value = 1900)
    private Integer ano;

    @NotBlank
    private String atores;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filme filme = (Filme) o;
        return Objects.equals(nome, filme.nome)
                && Objects.equals(generos, filme.generos)
                && Objects.equals(ano, filme.ano)
                && Objects.equals(atores, filme.atores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, generos, ano, atores);
    }
}
