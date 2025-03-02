import ApiAxios from "../utils/axiosInterceptor";

export const sendPago = async (prestamo, montoPago, metodoPago) => {

    try {
        const response = await ApiAxios.post(`/api/pagos`, {    
            prestamo: prestamo,
            montoPago: montoPago,
            metodoPago: metodoPago
        });
        return response.data;
    } catch (error) {
        console.error("Error al enviar el Pago");
        throw error;
    }
};

export const getPagos = async (idPrestamo) => {
    try {
        const response = await ApiAxios.get(`/api/pagos/prestamo/${idPrestamo}`);
        return response.data;
    } catch (error) {
        console.error("Error no se puede obtener el pago");
        throw error;
    }

};