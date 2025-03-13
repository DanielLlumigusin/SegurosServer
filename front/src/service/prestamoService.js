import ApiAxios from "../utils/axiosInterceptor"; 

export const solicitarPrestamo = async (monto, plazo, tasaInteres, tipoPago) => {
    try {
        const response = await ApiAxios.post(
            `/api/prestamos`,
            {
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


export const getPrestamoAprobado = async () =>{
    try {
        const response = (await ApiAxios.get(`/api/prestamos/aprobado`));
        return response.data;    
    } catch (error) {
        console.error("No se puede obtener los prestamos Aprobados", error);
        throw error;
    }
}

export const getPrestamoSolicitado = async () =>{
    try {
        const response = await ApiAxios.get(`/api/prestamos/solicitado`);
        return response.data;
    } catch (error) {
        console.error("No se puede obtener los prestamos aprobados", error);
        throw error;
    }
}

