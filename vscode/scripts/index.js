import getDados from "./getDados.js";

const elementos = {
    top10: document.querySelector('[data-name="top10"]'),
    lancamentos: document.querySelector('[data-name="lancamentos"]'),
    series: document.querySelector('[data-name="series"]'),
    searchResults: document.querySelector('[data-name="search-results"]')
};

// Function to create carousel items
function criarCarouselItens(elemento, dados) {
    const carousel = elemento.querySelector('.carousel');
    carousel.innerHTML = dados.map(filme => `
        <div class="carousel-item">
            <a href="/detalhes.html?id=${filme.id}">
                <img src="${filme.poster}" alt="${filme.titulo}">
            </a>
        </div>
    `).join('');
}

// Function to initialize carousel buttons
function inicializarCarousel(elemento) {
    const carousel = elemento.querySelector('.carousel');
    const prevButton = elemento.querySelector('#prev-button-series');
    const nextButton = elemento.querySelector('#next-button-series');

    let scrollAmount = 0;
    const itemWidth = document.querySelector('.carousel-item').offsetWidth + 10; // Adjust based on your CSS

    nextButton.addEventListener('click', () => {
        const maxScroll = carousel.scrollWidth - carousel.clientWidth;
        if (scrollAmount < maxScroll) {
            scrollAmount = Math.min(scrollAmount + itemWidth, maxScroll);
            carousel.style.transform = `translateX(-${scrollAmount}px)`;
        }
    });

    prevButton.addEventListener('click', () => {
        if (scrollAmount > 0) {
            scrollAmount = Math.max(scrollAmount - itemWidth, 0);
            carousel.style.transform = `translateX(-${scrollAmount}px)`;
        }
    });
}

function criarListaFilmes(elemento, dados) {
    const ulExistente = elemento.querySelector('ul');
    if (ulExistente) {
        elemento.removeChild(ulExistente);
    }

    if (elemento.dataset.name === 'series') {
        criarCarouselItens(elemento, dados);
        inicializarCarousel(elemento);
    } else {
        const ul = document.createElement('ul');
        ul.className = 'lista';
        const listaHTML = dados.map(filme => `
            <li>
                <a href="/detalhes.html?id=${filme.id}">
                    <img src="${filme.poster}" alt="${filme.titulo}">
                </a>
            </li>
        `).join('');

        ul.innerHTML = listaHTML;
        elemento.appendChild(ul);
    }
}

function lidarComErro(mensagemErro) {
    console.error(mensagemErro);
}

const searchInput = document.getElementById('search-input');
const searchIcon = document.getElementById('search-icon');

searchIcon.addEventListener('click', () => {
    if (searchInput.style.display === 'none') {
        searchInput.style.display = 'block';
        searchInput.focus();
    } else {
        const query = searchInput.value.trim();
        if (query) {
            getDados(`/series/search?query=${query}`)
                .then(data => {
                    criarListaFilmes(elementos.searchResults, data);
                    showSearchResults();
                })
                .catch(error => {
                    lidarComErro("Ocorreu um erro ao buscar as séries.");
                });
        }
    }
});

function showSearchResults() {
    const sections = document.querySelectorAll('main > section');
    sections.forEach(section => section.classList.add('hidden'));
    elementos.searchResults.classList.remove('hidden');
}

const categoriaSelect = document.querySelector('[data-categorias]');
const sectionsParaOcultar = document.querySelectorAll('.section');

categoriaSelect.addEventListener('change', function () {
    const categoria = document.querySelector('[data-name="categoria"]');
    const categoriaSelecionada = categoriaSelect.value;

    if (categoriaSelecionada === 'todos') {
        for (const section of sectionsParaOcultar) {
            section.classList.remove('hidden');
        }
        categoria.classList.add('hidden');
    } else {
        for (const section of sectionsParaOcultar) {
            section.classList.add('hidden');
        }
        categoria.classList.remove('hidden');
        getDados(`/series/categoria/${categoriaSelecionada}`)
            .then(data => {
                criarListaFilmes(categoria, data);
            })
            .catch(error => {
                lidarComErro("Ocorreu um erro ao carregar os dados da categoria.");
            });
    }
});

geraSeries();
function geraSeries() {
    const urls = ['/series/top10', '/series/lancamentos', '/series'];
    Promise.all(urls.map(url => getDados(url)))
        .then(data => {
            criarListaFilmes(elementos.top10, data[0]);
            criarListaFilmes(elementos.lancamentos, data[1]);
            criarListaFilmes(elementos.series, data[2].slice(0, 30)); // Adjust based on the number of items you want to display initially
        })
        .catch(error => {
            lidarComErro("Ocorreu um erro ao carregar os dados.");
        });
}
