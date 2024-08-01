# TvDb Website 🎥


Projeto ainda está em desenvolvimento! // Project still under development!


*english version is below the following portuguese explanation*


Demo: https://drive.google.com/file/d/1qkDShdahl9blEv5YOCZT5_RSrUQu2wfY/view?usp=drive_link


# PORTUGUESE

## Visão Geral

O TvDb é um aplicativo web projetado para fornecer aos usuários informações sobre séries de TV. O site oferece recursos para explorar novos lançamentos, títulos populares e informações detalhadas sobre séries individuais. 


Inicialmente, o projeto foi desenvolvido acompanhando o curso da Alura: 'Desenvolvendo Aplicações com Spring'. Após a conclusão do curso, o projeto foi expandido com novas funcionalidades e uma reformulação significativa. 


Atualmente, o projeto utiliza uma base de dados SQLite, disponível no repositório com 30 séries para testes, e permite adicionar novas séries através da parte de linha de comando do aplicativo via API da OMDB.

Já que as sinopses da API da OMDB estão em Inglês também utilizei o MyMemory, API de tradução grátis até 5000 chars por dia, para traduzir as sinopses.


![image](https://github.com/user-attachments/assets/58fad673-0aaf-4399-9494-b4a2615dc985)


![image](https://github.com/user-attachments/assets/9ee768f5-234b-4214-a8f3-827094b696bc)


## Atualizações
   *1.1 Adicionei campos de *Data de lançamento* e *média de avaliação* para cada episódio na aba de detalhes para cada série.
   *1.2 Adicionei um endpoint e para uma opção que mostra os 10 episódios mais bem avaliados de cada série na aba de detalhes para as mesmas.

   
## Recursos

    * Página Inicial: Seções para novos lançamentos, títulos populares e um carrossel para várias séries de TV. Veja capturas de tela e exemplos [aqui].
    * Funcionalidade de Busca: Permite pesquisar séries por nome.
    * Filtro por Categoria: Filtre séries por categorias como Comédia, Ação, Drama, etc.
    * Detalhes das Séries: Páginas dedicadas a informações detalhadas sobre cada série, incluindo temporadas e episódios.

## Tecnologias

    * Frontend: HTML, CSS e JavaScript para uma interface de usuário responsiva e interativa.
    * Backend: Java com Spring Boot para gerenciar solicitações de API e fornecer dados.

## Endpoints da API

A API backend, construída com Java e Spring Boot, oferece os seguintes endpoints:

    * Buscar Séries: GET /series/search?query={query} - Buscar séries com base em uma string de consulta, onde 'query' é o nome da série a ser buscada.
    * Obter Todas as Séries: GET /series - Retorna uma lista de todas as séries.
    * Top 10 Séries: GET /series/top10 - Retorna as 10 melhores séries.
    * Lançamentos: GET /series/lancamentos - Retorna novos lançamentos.
    * Obter Série por ID: GET /series/{id} - Retorna detalhes de uma série específica pelo seu ID.
    * Obter Todas as Temporadas: GET /series/{id}/temporadas/todas - Retorna todas as temporadas de uma série específica.
    * Obter Temporada por Número: GET /series/{id}/temporadas/{numero} - Retorna episódios de uma temporada específica.
    * Obter Séries por Categoria: GET /series/categoria/{nomeGenero} - Retorna séries com base em uma categoria especificada.
    * Obter Top 10 episódios por Série: GET /series/{id}/temporadas/top_episodes - Retorna 10 episódios em ordem decrescente se baseando em avaliação.

Exemplo de resposta JSON:
```json
{
        "id": 1,
        "titulo": "The Boys",
        "totalTemporadas": 5,
        "avaliacao": 8.7,
        "genero": "ACAO",
        "atores": "Karl Urban, Jack Quaid, Antony Starr",
        "poster": "https://m.media-amazon.com/images/M/MV5BYTY2ZjYyNGUtZGVkZS00MDNhLWIwMjMtZDk4MmQ5ZWI0NTY4XkEyXkFqcGdeQXVyMTY3MDE5MDY1._V1_SX300.jpg",
        "sinopse": "Um grupo de vigilantes partiu para derrubar super-heróis corruptos que abusam de seus superpoderes."
    }
```
## Detalhes do Backend

    * Controlador (SerieController): Gerencia solicitações HTTP relacionadas às séries de TV, como busca, recuperação de séries e filtragem por categoria.
    * Aplicativo de Linha de Comando (Principal): Anteriormente usado para interagir com os dados das séries a partir da linha de comando. Inclui funcionalidades para buscar e filtrar séries e episódios, e atualizar o banco de dados com informações de séries de uma API externa.

## Arquitetura: O backend segue o padrão MVC e utiliza Spring Boot para simplificar a gestão de dependências e a configuração do servidor.

* Testes: Para executar os testes automatizados, use o comando ./mvnw test.


## Integração com API

O backend interage com uma API externa para buscar dados sobre séries:

    URL Base: https://www.omdbapi.com/?t=
    Chave da API: Obtida a partir de uma variável de ambiente (OMDB_KEY). Configure a variável de ambiente no seu sistema ou arquivo .env.

## Tratamento de Erros: Caso a chave da API esteja incorreta ou a API esteja fora do ar, você receberá um erro 401 ou 500. Verifique a configuração da chave e a disponibilidade da API.
Uso

    Configuração: Certifique-se de que a API backend está em execução e acessível. A API deve estar configurada com os endpoints corretos e variáveis de ambiente.
    Acesso: Abra a página inicial em um navegador para explorar as séries de TV.
    Navegação: Use a barra de busca para encontrar séries específicas ou use o dropdown de categorias para filtrar o conteúdo.

## Como Utilizar

    Clone o repositório usando git clone <https://github.com/joaodias004/java_spring_show_db>.
    Navegue até o diretório do projeto e execute o arquivo TvDbApplication.java para iniciar o backend.
    Abra o arquivo index.html com um servidor ao vivo, como o Live Server no Visual Studio Code.

# ENGLISH
## Overview

TvDb is a web application designed to provide users with information about TV series. The site offers features to explore new releases, popular titles, and detailed information about individual series. 


Initially, the project was developed following the Alura course: 'Developing Applications with Spring'. After completing the course, I decided to expand the project by implementing new features and redesigning various aspects. 


Currently, the project uses an SQLite database, available in the repository with 30 series for testing, and allows adding new series through the command line part of the app via the OMDB API.


Since the OMBD's API plot information is written in english, and my project will be seen by mostly brazillian people, i decided to use MyMemory´s translating API to translate the text to portuguese.

## Updates:
    * 1.1: Added *Release date* and *Average rate* for every episode in the show's details.
    * 1.2: Added an endpoint that returns the top 10 episodes for the show, right next to "all seasons" on every show´s details.

## Features

    * Homepage: Sections for new releases, popular titles, and a carousel for various TV series.
    * Search Functionality: Allows searching for series by name.
    * Category Filter: Filter series by categories such as Comedy, Action, Drama, etc.
    * Series Details: Pages dedicated to detailed information about each series, including seasons and episodes.

## Technologies

    * Frontend: HTML, CSS, and JavaScript for a responsive and interactive user interface.
    * Backend: Java with Spring Boot to manage API requests and provide data.

## API Endpoints

The backend API, built with Java and Spring Boot, provides the following endpoints:

    * Search Series: GET /series/search?query={query} - Search for series based on a query string, where 'query' is the name of the series to be searched.
    * Get All Series: GET /series - Returns a list of all series.
    * Top 10 Series: GET /series/top10 - Returns the top 10 series.
    * Releases: GET /series/lancamentos - Returns new releases.
    * Get Series by ID: GET /series/{id} - Returns details of a specific series by its ID.
    * Get All Seasons: GET /series/{id}/temporadas/todas - Returns all seasons of a specific series.
    * Get Season by Number: GET /series/{id}/temporadas/{numero} - Returns episodes of a specific season.
    * Get Series by Category: GET /series/categoria/{nomeGenero} - Returns series based on a specified category.
    * Get Top 10 Episodes by Series: GET /series/{id}/seasons/top_episodes - Returns 10 episodes in descending order based on rating.

Example JSON Response: 

```json
{
        "id": 1,
        "titulo": "The Boys",
        "totalTemporadas": 5,
        "avaliacao": 8.7,
        "genero": "ACAO",
        "atores": "Karl Urban, Jack Quaid, Antony Starr",
        "poster": "https://m.media-amazon.com/images/M/MV5BYTY2ZjYyNGUtZGVkZS00MDNhLWIwMjMtZDk4MmQ5ZWI0NTY4XkEyXkFqcGdeQXVyMTY3MDE5MDY1._V1_SX300.jpg",
        "sinopse": "Um grupo de vigilantes partiu para derrubar super-heróis corruptos que abusam de seus superpoderes."
    }
```


Backend Details

    * Controller (SerieController): Manages HTTP requests related to TV series, such as searching, retrieving series, and filtering by category.
    * Command-Line Application (Principal): Previously used to interact with series data from the command line. Includes functionality to search and filter series and episodes, and update the database with series information from an external API.

Architecture: The backend follows the MVC pattern and uses Spring Boot to simplify dependency management and server configuration.

## Tests: To run automated tests, use the command ./mvnw test.
API Integration

The backend interacts with an external API to fetch data about series:

    Base URL: https://www.omdbapi.com/?t=
    API Key: Obtained from an environment variable (OMDB_KEY). Configure the environment variable on your system or in a .env file.

Error Handling: If the API key is incorrect or the API is down, you will receive a 401 or 500 error. Check the key configuration and API availability.
Usage

    * Setup: Ensure the backend API is running and accessible. The API should be configured with the correct endpoints and environment variables.
    * Access: Open the homepage in a browser to explore TV series.
    * Navigation: Use the search bar to find specific series or use the category dropdown to filter content.

## How to Use

    * Clone the repository using git clone <https://github.com/joaodias004/java_spring_show_db>.
    * Navigate to the project directory and run the TvDbApplication.java file to start the backend.
    * Open the index.html file with a live server, such as the Live Server in Visual Studio Code.
