import ApiAxios from "../utils/axiosInterceptor";
import { URLBASE } from "../utils/tools";

const getTablaAmortizacion = async (usuarioId) => {
    const response = await ApiAxios.get(`${URLBASE}/api/tablas-amortizacion/${usuarioId}`,
    )
    return response.data;
}