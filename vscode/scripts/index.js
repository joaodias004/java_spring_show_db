import getDados from "./getDados.js";

const elementos = {
    top10: document.querySelector('[data-name="top10"]'),
    lancamentos: document.querySelector('[data-name="lancamentos"]'),
    series: document.querySelector('[data-name="series"]'),
    searchResults: document.querySelector('[data-name="search-results"]') // Add this line
};

function criarListaFilmes(elemento, dados) {
    const ulExistente = elemento.querySelector('ul');
    if (ulExistente) {
        elemento.removeChild(ulExistente);
    }

    const ul = document.createElement('ul');
    ul.className = 'lista';
    const listaHTML = dados.map((filme) => `
        <li>
            <a href="/detalhes.html?id=${filme.id}">
                <img src="${filme.poster}" alt="${filme.titulo}">
            </a>
        </li>
    `).join('');

    ul.innerHTML = listaHTML;
    elemento.appendChild(ul);
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
            criarListaFilmes(elementos.series, data[2].slice(0, 5));
        })
        .catch(error => {
            lidarComErro("Ocorreu um erro ao carregar os dados.");
        });
}
