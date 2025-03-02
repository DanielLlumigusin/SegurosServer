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

//Actualizar los prestamos

export const updatePrestamo = async (prestamo) => {
    try {
        await ApiAxios.put("/admin/prestamos", prestamo);
    } catch (error) {
        console.error("Error al actualizar el prestamo", error);
    }
}