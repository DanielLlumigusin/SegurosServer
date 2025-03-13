import ApiAxios from "../utils/axiosInterceptor"
export const getAllPagos = async () => {
    try {
        const response = await ApiAxios.get();
        return response.data;
    } catch (error) {
        console.error("No se pudo obtener todos los pagos: ", error);
        throw error;
    }
}

export const getPagoByPrestamoId = async (prestamoId) => {
    if (!prestamoId) {
        throw new Error("prestamoId no es válido.");
    }

    try {
        const response = await ApiAxios.get("/admin/pagos/prestamo", {
            params: { prestamoId }
        });

        if (response && response.data) {
            return response.data;
        } else {
            throw new Error("La respuesta de la API no contiene datos.");
        }
    } catch (error) {
        console.error("Error al obtener pagos por el préstamo:", error);
        throw new Error(`Error al obtener pagos para el prestamoId: ${prestamoId}`);
    }
};

export const aprobarPago = async (pagoId) => {
    try {
        const response = await ApiAxios.put("/admin/pagos/aprobar/");
        return response.data;
    } catch (error) {
        console.error("No se pudo aprobar el pago", error);
        throw error;;
    }
}

