const baseURL = 'http://localhost:8080';

export default function getDados(endpoint) {
    const token = localStorage.getItem('token');

    return fetch(`${baseURL}${endpoint}`, {
        headers: {
            'Authorization': `Bearer ${token}`
        }
    })
    .then(response => response.json())
    .catch(error => {
        console.error('Erro ao acessar o endpoint:', error);
    });
}
