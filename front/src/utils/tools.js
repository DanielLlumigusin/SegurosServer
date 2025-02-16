import ApiAxios from "./axiosInterceptor";

export const URLBASE = 'http://localhost:8080';

export const getDataUsername = async (username) => {
    try {
        const response = await ApiAxios.get(`${URLBASE}/api/usuarios/username`, {
            params: {
                username: username,
            },
        });
        return response.data; // Devuelve los datos del usuario
    } catch (error) {
        console.error("Error en la solicitud:", error);
        throw new Error("Error al obtener los datos del usuario"); // Lanza una excepción
    }
};
