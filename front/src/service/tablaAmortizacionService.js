import ApiAxios from "../utils/axiosInterceptor";

 export const getTablaAmortizacion = async () => {
    try {
        const response = await ApiAxios.get(`/api/tablas-amortizacion/prestamo`)
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }

}