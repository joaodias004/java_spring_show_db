package com.example.demo.principal;

import com.example.demo.model.*;
import com.example.demo.repository.SerieRepository;
import com.example.demo.service.ConsumoAPI;
import com.example.demo.service.ConverteDados;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
     private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = System.getenv("OMDB_KEY");

    private List<DadosSerie> dadosSerie = new ArrayList<>();

    private SerieRepository repository;

    List<Serie> series = new ArrayList<>();

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas
                    4 - Buscar série por titulo
                    5 - Buscar série por ator
                    6 - Top 10 séries
                    7 - Buscar série por categoria
                    8 - Filtro
                    9 - Busca de episódios por trecho
                    10 - Top 10 episódios por série
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    BuscarSeriePorCategoria();
                    break;
                case 8:
                    FiltroMultiplo();
                case 9:
                    BuscaEpisodiosPorTrecho();
                    break;
                case 10:
                    topEpisodiosPorSerie();
                    break;
                case 11:
                    buscarEpisodiosPorData();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarEpisodiosPorData() {
        buscarSeriePorTitulo();
        if (serieBusca.isPresent()) {
            System.out.println("Qual o ano inicial?");
            int anoInicial = leitura.nextInt();
            leitura.nextLine();
            System.out.println("Qual o ano limite?");
            int anoLimite = leitura.nextInt();
            leitura.nextLine();
            Serie serie = serieBusca.get();
            List<Episodio> episodios = repositorio.findBySerie(serie);
            List<Episodio> episodiosPorData = episodios.stream()
                    .filter(e -> e.getDataLancamento().getYear() >= anoInicial && e.getDataLancamento().getYear() <= anoLimite)
                    .collect(Collectors.toList());
            episodiosPorData.forEach(System.out::println);
        }
    }

    private Optional<Serie> serieBusca;
    private void topEpisodiosPorSerie(){
        buscarSeriePorTitulo();
        if(serieBusca.isPresent()){
            Serie serie = serieBusca.get();
            List<Episodio> topEpisodios = repositorio.topEpisodiosPorSerie(serie);
            topEpisodios.forEach(e ->
                    System.out.printf("Série: %s Temporada %s - Episódio %s - %s Avaliação %s\n",
                            e.getSerie().getTitulo(), e.getTemporada(),
                            e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao() ));
        }
    }


    private void BuscaEpisodiosPorTrecho(){
        System.out.println("Qual o nome do episódio para busca?");
        var trechoProcurado = leitura.nextLine();
        List<Episodio> episodiosEncontrados = repositorio.episodiosPorTrecho(trechoProcurado);
        episodiosEncontrados.forEach(e ->
                System.out.printf("Série: %s Temporada %s - Episódio %s - %s\n",
                        e.getSerie().getTitulo(), e.getTemporada(),
                        e.getNumeroEpisodio(), e.getTitulo()));
    }


        private void FiltroMultiplo() {
        System.out.println("Série com até quantas temporadas?");
        var limiteTemporadas = leitura.nextInt();
        leitura.nextLine();
        System.out.println("Deseja buscar séries a partir de que nota?");
        var avalicao = leitura.nextDouble();
        leitura.nextLine();
        List <Serie> seriesFiltradas = repositorio.findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual(limiteTemporadas, avalicao);
        System.out.println("Séries de acordo com seu filtro: ");
        seriesFiltradas.forEach(s ->
                System.out.println(s.getTitulo() + " Temporadas: " + s.getTotalTemporadas() + " Avaliação: " + s.getAvaliacao())
        );
    }

    private void buscarSeriePorAtor() {
        System.out.println("Qual o nome do ator?");
        var nomeAtor = leitura.nextLine();
        System.out.println("Deseja buscar séries a partir de que nota?");
        var avalicao = leitura.nextDouble();
        List <Serie> seriesEncontradas = repositorio.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor,avalicao);
        System.out.println("Séries em que " + nomeAtor + " Trabalhou: ");
        seriesEncontradas.forEach(s ->
                System.out.println(s.getTitulo() + " Avaliação: " + s.getAvaliacao()));
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Escolha um série pelo nome: ");
        var nomeSerie = leitura.nextLine();
        serieBusca = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBusca.isPresent()) {
            System.out.println("Dados da série: " + serieBusca.get());

        } else {
            System.out.println("Série não encontrada!");
        }

    }

    private SerieRepository repositorio;

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie (dados);
        repositorio.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarTop5Series(){
        List<Serie> serieTop = repositorio.findTop10ByOrderByAvaliacaoDesc();
        serieTop.forEach(s ->
                System.out.println(s.getTitulo() + " Nota: " + s.getAvaliacao()));
     }

    private void buscarEpisodioPorSerie(){
        listarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome");
        var nomeSerie = leitura.nextLine();

        Optional<Serie> serie = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if(serie.isPresent()) {

            var serieEncontrada = serie.get();

            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }
        else {
            System.out.println("Série não encontrada");
        }
    }
    private void listarSeriesBuscadas(){
        series = repositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void BuscarSeriePorCategoria(){
        System.out.println("Digite a categoria/gênero desejado:");
        var nomeGenero = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        List<Serie> seriePorCategoria= repositorio.findByGenero(categoria);
        System.out.println("Séries da categoria: " + nomeGenero);
        seriePorCategoria.forEach(System.out::println);

    }

}
