import { useEffect, useState } from "react";
import { getTablaAmortizacion } from "../service/tablaAmortizacionService";

const useTablaAmortizacion = () => {
    const [tablaAmortizacion, setTablaAmortizacion] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const getTablaAmortizacionByPrestamoId = async () => {
        try {
            setLoading(true);
            setError(null);

            const response = await getTablaAmortizacion();
            setTablaAmortizacion(response);
        } catch (err) {
            setError("Error al obtener la tabla de amortización");
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        getTablaAmortizacionByPrestamoId();
    },[])

    return {tablaAmortizacion, loading, error };
};

export default useTablaAmortizacion;
