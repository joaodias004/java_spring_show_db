import getDados from "./getDados.js";

const params = new URLSearchParams(window.location.search);
const serieId = params.get('id');
const listaTemporadas = document.getElementById('temporadas-select');
const fichaSerie = document.getElementById('temporadas-episodios');
const fichaDescricao = document.getElementById('ficha-descricao');


function carregarTemporadas() {
    getDados(`/series/${serieId}/temporadas/todas`)
        .then(data => {
            const temporadasUnicas = [...new Set(data.map(temporada => temporada.temporada))];
            listaTemporadas.innerHTML = '';

            const optionDefault = document.createElement('option');
            optionDefault.value = '';
            optionDefault.textContent = 'Selecione a temporada';
            listaTemporadas.appendChild(optionDefault);

            temporadasUnicas.forEach(temporada => {
                const option = document.createElement('option');
                option.value = temporada;
                option.textContent = temporada;
                listaTemporadas.appendChild(option);
            });

            const optionTodos = document.createElement('option');
            optionTodos.value = 'todas';
            optionTodos.textContent = 'Todas as temporadas';
            listaTemporadas.appendChild(optionTodos);
        })
        .catch(error => {
            console.error('Erro ao obter temporadas:', error);
        });
}


function carregarEpisodios() {
    if (!listaTemporadas.value) return;

    getDados(`/series/${serieId}/temporadas/${listaTemporadas.value}`)
        .then(data => {
            const temporadasUnicas = [...new Set(data.map(temporada => temporada.temporada))];
            fichaSerie.innerHTML = ''; 
            temporadasUnicas.forEach(temporada => {
                const ul = document.createElement('ul');
                ul.className = 'episodios-lista';

                const episodiosTemporadaAtual = data.filter(serie => serie.temporada === temporada);

                const listaHTML = episodiosTemporadaAtual.map(serie => `
                    <li>
                        ${serie.numeroEpisodio} - ${serie.titulo}
                    </li>
                `).join('');
                ul.innerHTML = listaHTML;
                
                const paragrafo = document.createElement('p');
                const linha = document.createElement('br');
                paragrafo.textContent = `Temporada ${temporada}`;
                fichaSerie.appendChild(paragrafo);
                fichaSerie.appendChild(linha);
                fichaSerie.appendChild(ul);
            });

            fichaSerie.style.display = 'block'; 
        })
        .catch(error => {
            console.error('Erro ao obter episódios:', error);
        });
}

function carregarInfoSerie() {
    getDados(`/series/${serieId}`)
        .then(data => {
            fichaDescricao.innerHTML = `
                <img src="${data.poster}" alt="${data.titulo}" />
                <div>
                    <h2>${data.titulo}</h2>
                    <div class="descricao-texto">
                        <p><b>Média de avaliações:</b> ${data.avaliacao}</p>
                        <p>${data.sinopse}</p>
                        <p><b>Estrelando:</b> ${data.atores}</p>
                    </div>
                </div>
            `;
        })
        .catch(error => {
            console.error('Erro ao obter informações da série:', error);
        });
}

listaTemporadas.addEventListener('change', carregarEpisodios);

carregarInfoSerie();
carregarTemporadas();
