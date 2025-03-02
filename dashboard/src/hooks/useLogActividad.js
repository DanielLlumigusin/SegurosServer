import { useState, useEffect } from "react";
import { getLogsActividad } from "../service/logActividadService";

const useLogActividad = () => {
    const [loadingLogActividad, setLoadingLogActividad] = useState(false);
    const [errorLogActividad, setErrorLogActividad] = useState('');
    const [listaLogActividad, setListaLogActividad] = useState([]);

    const getLogActividad = async () => {
        try {
            setLoadingLogActividad(true);
            const response = await getLogsActividad();
            setListaLogActividad(response)
        } catch (error) {
            console.error("Error al obtener los registro de actividad", error);
            setErrorLogActividad("Error al cargar los registros de actividad");
        } finally {
            setLoadingLogActividad(false);
        }
    };

    useEffect(() => {
        getLogActividad();
    }, []);

    return {
        listaLogActividad,
        errorLogActividad,
        loadingLogActividad
    }
}

export default useLogActividad;