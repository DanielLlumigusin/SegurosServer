import ApiAxios from "../utils/axiosInterceptor"; 
import { URLBASE } from "../utils/tools"; 

const solicitarPrestamo = async (monto, plazo, tasaInteres, tipoPago, usuario) => {
    const token = localStorage.getItem("token");

    if (!token) {
        throw new Error("No se encontró el token de autenticación. Por favor, inicie sesión.");
    }

    const response = await ApiAxios.post(
        `${URLBASE}/api/prestamos`,
        {
            usuario:usuario,
            montoSolicitado: parseFloat(monto),
            plazoAmortizacion: parseInt(plazo),
            tasaInteres: parseFloat(tasaInteres),
            tipoPago: tipoPago
        }
    );

    return response.data;
};

const getPrestamoAprobadoByUsuarioId = async (usuarioId) =>{
    const response = (await ApiAxios.get(`${URLBASE}/api/prestamos/usuario/${usuarioId}/aprobado`));
    return response.data;
}

const getPrestamoSolicitadoByUsuarioId = async (usuarioId) =>{
    const response = await ApiAxios.get(`${URLBASE}/api/prestamos/usuario/${usuarioId}/solicitados`);
    return response.data;
}

export default { solicitarPrestamo, getPrestamoAprobadoByUsuarioId, getPrestamoSolicitadoByUsuarioId };