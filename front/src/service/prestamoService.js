import ApiAxios from "../utils/axiosInterceptor"; 

export const solicitarPrestamo = async (monto, plazo, tasaInteres, tipoPago, usuario) => {
    try {
        const response = await ApiAxios.post(
            `/api/prestamos`,
            {
                usuario:usuario,
                montoSolicitado: parseFloat(monto),
                plazoAmortizacion: parseInt(plazo),
                tasaInteres: parseFloat(tasaInteres),
                tipoPago: tipoPago
            }
        );
    } catch (error) {
        console.error("No se puede registrar el prestamo ", error);
        throw error;
    }
    return response.data;
};


export const getPrestamoAprobadoByUsuarioId = async (usuarioId) =>{
    try {
        const response = (await ApiAxios.get(`/api/prestamos/usuario/${usuarioId}/aprobado`));
        return response.data;    
    } catch (error) {
        console.error("No se puede obtener los prestamos Aprobados", error);
        throw error;
    }
}

export const getPrestamoSolicitadoByUsuarioId = async (usuarioId) =>{
    try {
        const response = await ApiAxios.get(`/api/prestamos/usuario/${usuarioId}/solicitados`);
        return response.data;
    } catch (error) {
        console.error("No se puede obtener los prestamos aprobados", error);
        throw error;
    }
}

