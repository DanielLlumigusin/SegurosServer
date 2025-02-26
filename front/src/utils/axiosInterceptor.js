import axios from 'axios';

const ApiAxios = axios.create();

ApiAxios.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");
    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

ApiAxios.interceptors.response.use((response) => {
    return response;
}, (error) => {
    if (error.response && error.response.status === 401) {
        //localStorage.removeItem("token");
        //window.location.href = "/"; 
    }
    return Promise.reject(error);
});

export default ApiAxios;