import ApiAxios from "../utils/axiosInterceptor";

export const loginService = async (username, password) => {
    try {
        await ApiAxios.post("/auth/login", { username: username, password: password });
    } catch (error) {
        console.error("Error en el login", error);
        throw error;
    }
}

export const logoutService = async () => {
    try {
        await ApiAxios.post("/auth/logout");
    } catch (error) {
        console.error("Error al cerrar sesion", error);
        throw error;
    }
}

export const checkService = async () => {
    try {
        const response = await ApiAxios.get("/auth/check");
        return response.data;
    } catch (error) {
        console.error("Usuario no Autentificado");
    }
}