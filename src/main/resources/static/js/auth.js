// auth.js

// Token management
function saveToken(token) {
    localStorage.setItem("jwtToken", token);
    console.log('Token guardado:', token);
}

function getToken() {
    const token = localStorage.getItem("jwtToken");
    console.log('Token recuperado:', token);
    return token;
}

function removeToken() {
    console.log('Removiendo token');
    localStorage.removeItem("jwtToken");
}

function isAuthenticated() {
    const token = getToken();
    if (!token) return false;
    
    try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        const isValid = payload.exp * 1000 > Date.now();
        console.log('Estado de autenticación:', isValid);
        return isValid;
    } catch {
        return false;
    }
}

// Headers management
function getAuthHeaders() {
    const token = getToken();
    const headers = token ? {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
    } : {
        'Content-Type': 'application/json'
    };
    console.log('Headers generados:', headers);
    return headers;
}

// Verificación de rutas protegidas
async function verifyProtectedRoute() {
    const protectedRoutes = [ '/receta'];
    const currentPath = window.location.pathname;
    console.log('Verificando ruta protegida:', currentPath);
    
    if (protectedRoutes.some(route => currentPath.startsWith(route))) {
        const token = getToken();
        console.log('Token para ruta protegida:', token);
        
        if (!token) {
            console.log('No hay token, redirigiendo a login');
            window.location.href = '/login';
            return false;
        }

        try {
            const response = await fetch('/api/recetas/buscar', {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                console.log('Token inválido, redirigiendo a login');
                removeToken();
                window.location.href = '/login';
                return false;
            }

            return true;
        } catch (error) {
            console.error('Error verificando ruta protegida:', error);
            return false;
        }
    }
    return true;
}
// Añade esta función al inicio de tu auth.js
async function navegarABuscar() {
    if (!isAuthenticated()) {
        // Si no está autenticado, redirigir al login
        window.location.href = '/login';
        return;
    }

    const token = getToken();
    try {
        // Primera verificación de autenticación antes de navegar
        const response = await fetch('/api/recetas/buscar', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            // Navegar a /buscar sin utilizar token en la URL
            window.location.href = '/buscar';
        } else {
            // Si el token no es válido, eliminar el token y redirigir al login
            removeToken();
            window.location.href = '/login';
        }
    } catch (error) {
        console.error('Error al verificar autenticación:', error);
        window.location.href = '/login';
    }
}

// UI updates
function updateUIAuthentication() {
    const isAuth = isAuthenticated();
    console.log('Actualizando UI, autenticado:', isAuth);

    document.querySelectorAll('.show-for-anonymous').forEach(element => {
        element.style.display = isAuth ? 'none' : 'inline-block';
    });

    document.querySelectorAll('.show-for-auth').forEach(element => {
        element.style.display = isAuth ? 'inline-block' : 'none';
    });
}
function displayResults(results) {
    console.log('Mostrando resultados:', results);
    const resultsSection = document.querySelector('section:nth-of-type(2)');
    let html = '<h2>Resultados de la búsqueda</h2>';
    
    if (!results || results.length === 0) {
        html += '<div><p>No se encontraron recetas.</p></div>';
    } else {
        html += '<div><ul>';
        results.forEach(receta => {
            html += `
                <li>
                    <h3>${receta.nombre}</h3>
                    <p><strong>Tipo de Cocina:</strong> ${receta.tipoCocina}</p>
                    <p><strong>Dificultad:</strong> ${receta.dificultad}</p>
                    <p><strong>Tiempo de Cocción:</strong> ${receta.tiempoCoccion} minutos</p>
                    <a href="/receta/detalle?id=${receta.id}">Ver detalles</a>
                </li>
            `;
        });
        html += '</ul></div>';
    }
    
    resultsSection.innerHTML = html;
}

function displayError(message) {
    console.log('Mostrando error:', message);
    const resultsSection = document.querySelector('section:nth-of-type(2)');
    resultsSection.innerHTML = `
        <h2>Error</h2>
        <div>
            <p style="color: red;">${message}</p>
        </div>
    `;
}

// Login function
async function login(username, password) {
    try {
        console.log('Iniciando login para:', username);
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        console.log('Status login:', response.status);

        if (!response.ok) {
            throw new Error('Error de autenticación');
        }

        const data = await response.json();
        console.log('Datos login:', data);

        if (data.token) {
            console.log('Token recibido en login:', data.token);
            saveToken(data.token);
            updateUIAuthentication();
            window.location.href = '/';
        }
    } catch (error) {
        console.error('Error de login:', error);
        const errorMessage = document.getElementById('errorMessage');
        if (errorMessage) {
            errorMessage.textContent = error.message;
            errorMessage.style.display = 'block';
        }
    }
}

// Search function
async function buscarRecetas(searchTerm) {
    console.log('Iniciando búsqueda:', searchTerm);
    try {
        const headers = getAuthHeaders();
        console.log('Headers de búsqueda:', headers);

        const response = await fetch(`/api/recetas/buscar?nombre=${encodeURIComponent(searchTerm)}`, {
            method: 'GET',
            headers: headers
        });

        console.log('Status de respuesta:', response.status);

        if (!response.ok) {
            if (response.status === 401) {
                removeToken();
                window.location.href = '/login';
                throw new Error('Sesión expirada');
            }
            throw new Error('Error en la búsqueda');
        }

        const data = await response.json();
        console.log('Resultados:', data);
        return data;
    } catch (error) {
        console.error('Error completo:', error);
        throw error;
    }
}


// Event Listeners
document.addEventListener('DOMContentLoaded', async function() {
    
    console.log('DOM cargado, inicializando');
    const redirectToken = sessionStorage.getItem('redirectToken');
    if (redirectToken) {
        sessionStorage.removeItem('redirectToken');
        saveToken(redirectToken);
    }
    if (!(await verifyProtectedRoute())) {
        return;
    }

    updateUIAuthentication();

    // Login form handler
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            login(username, password);
        });
    }

    // Logout handler
    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', function(e) {
            e.preventDefault();
            removeToken();
            updateUIAuthentication();
            window.location.href = '/';
        });
    }

    // Search form handler
    const searchForm = document.querySelector('form[action="/buscar"]');
    if (searchForm) {
        searchForm.addEventListener('submit', async function(e) {
            e.preventDefault();
            const searchInput = this.querySelector('input[name="nombre"]');
            if (!isAuthenticated()) {
                console.log('Intento de búsqueda sin autenticación');
                window.location.href = '/login';
                return;
            }
            try {
                const results = await buscarRecetas(searchInput.value);
                displayResults(results);
            } catch (error) {
                displayError(error.message);
            }
        });
    }

    const buscarLinks = document.querySelectorAll('a[href="/buscar"]');
    buscarLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            navegarABuscar();
        });
    });
});

// Display functions

