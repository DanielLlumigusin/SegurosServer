import { useState, useEffect } from "react";
import { calcularCuota, generarTablaAmortizacion } from "../calcularCuota";

const usePrestamo = (monto, plazo, tasaInteres, tipoPago) => {
    const [cuota, setCuota] = useState(0);
    const [tablaAmortizacion, setTablaAmortizacion] = useState([]);

    useEffect(() => {
        setCuota(calcularCuota(monto, plazo, tasaInteres, tipoPago).toFixed(2));
        setTablaAmortizacion(generarTablaAmortizacion(monto, plazo, tasaInteres, tipoPago));
    }, [monto, plazo, tasaInteres, tipoPago]);

    return { cuota, tablaAmortizacion };
};

export default usePrestamo;