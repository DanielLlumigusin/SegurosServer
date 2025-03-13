import { useState } from "react";
import { aprobarPago, getAllPagos, getPagoByPrestamoId } from "../service/pagoService";

const usePago = () => {

    const [listPago, setListPago] = useState([]);
    const [loadingPago, setLoadingPago] = useState(false);
    const [mensajePago, setMensajePago] = useState("");
    const [errorPago, setErrorPago] = useState("");

    const clean = () => {
        setLoadingPago(true);
        setErrorPago('');
        setMensajePago('');
        setListPago([]);
    }

    const obtenerPagoByPrestamoId = async (prestamoId) => {
        try {
            clean();
            const response = await getPagoByPrestamoId(prestamoId);
            setListPago(response);
        } catch (error) {
            setErrorPago("Error no se puede obtener el o los pagos");
            console.error("Error al obtener pagos:", error);
        } finally {
            setLoadingPago(false);
        }
    };

    const checkPago = async (pagoId) => {
        try {
            clean();
            const response = await aprobarPago(pagoId);
            setMensajePago(response);
        } catch (error) {
            setErrorPago("Error no se puede aprobar el pago");
        } finally {
            setLoadingPago(false);
        }
    }

    return {
        obtenerPagoByPrestamoId,
        loadingPago,
        errorPago,
        mensajePago,
        listPago,
        checkPago
    };
}

export default usePago;
