package com.example.demo.SerieDTO;

import java.time.LocalDate;

public record EpisodioDTO(Integer temporada, Integer numeroEpisodio, String titulo, Double avaliacao, LocalDate dataDeLancamento) {
}

