import ApiAxios from "../utils/axiosInterceptor";
import { URLBASE } from "../utils/tools";

export const sendPago = async (prestamo, montoPago, metodoPago) => {

    const response = await ApiAxios.post(`${URLBASE}/api/pagos`, {

        prestamo: prestamo,
        montoPago: montoPago,
        metodoPago: metodoPago

    });
    return response.data;
};

export const getPagos = async (idPrestamo) => {
    const response = await ApiAxios.get(`${URLBASE}/api/pagos/prestamo/${idPrestamo}`);

    return response.data;

};