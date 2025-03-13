import ApiAxios from "../utils/axiosInterceptor";

//Obtener todos los prestamos

export const getAllPrestamos = async () => {
    try {
        const response = await ApiAxios.get('/admin/prestamos');
        return response.data;
    } catch (error) {
        console.error("Error al obtener los prestamos", error);
        throw error;
    }
}

//Obtener los prestamos por el usuario 
export const getPrestamoByUsuarioId = async (usuarioId) => {
    try {
        const response = await ApiAxios.get(`/admin/prestamos/usuario`, {params:{usuarioId}});
        return response.data;
    } catch (error) {
        console.error("No se pudo obtener los prestamos por el usuario", error);
        throw error;
    }
}

//Aprobar el prestamo
export const aprobarPrestamo = async (prestamo) => {
    try {
        const response = await ApiAxios.put('/admin/prestamos', {
            prestamoId: prestamo.prestamoId 
        });
        return response.data;
    } catch (error) {
        console.error("Error en aprobar el préstamo: ", error.response?.data || error.message);
        throw error;
    }
};
