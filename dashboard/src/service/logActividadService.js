import ApiAxios from "../utils/axiosInterceptor";

//Obtener todos los Logs

export const getLogsActividad = async () => {
    try {
        const response = await ApiAxios.get('/admin/logs-actividades');
        return response.data;
    } catch (error) {
        console.error(error);
        throw error;
    }
}