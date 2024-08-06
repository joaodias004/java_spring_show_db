const tabButtons = document.querySelectorAll('.tab-button');
        const forms = document.querySelectorAll('.form-container');

        tabButtons.forEach(button => {
            button.addEventListener('click', () => {
                const targetForm = button.getAttribute('data-tab');
                
                forms.forEach(form => form.classList.remove('active'));
                tabButtons.forEach(btn => btn.classList.remove('active'));
                
                document.getElementById(targetForm).classList.add('active');
                button.classList.add('active');
            });
        });

        const handleFormSubmission = (event, url) => {
            event.preventDefault();
            
            const username = event.target.elements.username.value;
            const password = event.target.elements.password.value;
            
            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password })
            })
            .then(response => response.text())
            .then(data => {
                if (url === 'http://localhost:8080/login') {
                    if (data) {
                        localStorage.setItem('token', data);
                        window.location.href = '/index.html';
                    } else {
                        alert('Login falhou');
                    }
                } else {
                    alert(data);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Ocorreu um erro');
            });
        };

        document.getElementById('loginFormContent').addEventListener('submit', event => {
            handleFormSubmission(event, 'http://localhost:8080/login');
        });

        document.getElementById('registerFormContent').addEventListener('submit', event => {
            handleFormSubmission(event, 'http://localhost:8080/register');
        });