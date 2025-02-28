import axios from 'axios';
import { URLBASE } from '../utils/tools.js';

const ApiAxios = axios.create({
    baseURL: URLBASE,
    withCredentials: true 
});

// Interceptor de solicitudes: Ya no es necesario añadir manualmente el token
ApiAxios.interceptors.request.use((config) => {
    return config;
}, (error) => {
    return Promise.reject(error);
});

// Interceptor de respuestas: Manejo de errores 401 (sesión expirada o no autorizada)
ApiAxios.interceptors.response.use((response) => {
    return response;
}, (error) => {
    if (error.response && error.response.status === 401) {
        // Redirigir a login si el usuario no está autenticado
        window.location.href = "/login"; 
    }
    return Promise.reject(error);
});

export default ApiAxios;
