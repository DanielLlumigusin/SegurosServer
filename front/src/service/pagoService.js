import ApiAxios from "../utils/axiosInterceptor";

//Solicitar un Pago

export const sendPago = async (montoPago, metodoPago) => {
    try {
        
        const response = await ApiAxios.post(`/api/pagos/solicitar`, {    
            montoPago: montoPago,
            metodoPago: metodoPago
        });
        return response.data;
    } catch (error) {
        console.error("Error al enviar el Pago:", error.response?.data || error.message);
        throw error;
    }
};


export const getPagos = async () => {
    try {
        const response = await ApiAxios.get(`/api/pagos/prestamo`);

        if (response.status === 200) {
            return response.data;  // Devuelve los pagos obtenidos si la respuesta es correcta
        }

        if (response.status === 204) {
            return { message: "No hay pagos registrados." };  // No hay contenido, pero no hubo error
        }

        if (response.status === 404) {
            return { message: "No se encontraron pagos para este préstamo." };  // No se encontraron datos
        }

        return { message: "Respuesta inesperada del servidor." };  // Caso en que la respuesta no sea 200, 404 ni 204.

    } catch (error) {
        console.error("Error al obtener los pagos:", error);
        return { message: "Error al obtener los pagos, intente más tarde." };  // Devuelves un mensaje genérico de error
    }
};
