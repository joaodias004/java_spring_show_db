package com.example.demo.repository;

import com.example.demo.model.Categoria;
import com.example.demo.model.Episodio;
import com.example.demo.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainingIgnoreCase (String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, double avaliacao);

    List<Serie> findTop10ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria);

    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(int limiteTemporadas, double avalicao);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoProcurado%")
    List<Episodio> episodiosPorTrecho(String trechoProcurado);;

    @Query("SELECT e FROM Episodio e WHERE e.serie = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> topEpisodiosPorSerie(Serie serie);

    @Query("SELECT e FROM Episodio e WHERE e.serie = :serie AND FUNCTION('YEAR', e.dataLancamento) BETWEEN :anoInicial AND :anoLimite")
    List<Episodio> episodiosPorData(@Param("serie") Serie serie, @Param("anoInicial") int anoInicial, @Param("anoLimite") int anoLimite);

    @Query("SELECT e FROM Episodio e WHERE e.serie = :serie")
    List<Episodio> findBySerie(@Param("serie") Serie serie);
}


