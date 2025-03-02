import ApiAxios from "../utils/axiosInterceptor";

 export const getTablaAmortizacion = async (usuarioId) => {
    const response = await ApiAxios.get(`/api/tablas-amortizacion/${usuarioId}`,
    )
    return response.data;
}