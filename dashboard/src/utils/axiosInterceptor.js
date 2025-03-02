import axios from 'axios';

const URL_BASE = import.meta.env.VITE_URL_BASE;
const ApiAxios = axios.create({
    baseURL: URL_BASE,
    withCredentials: true  
});


export default ApiAxios;
