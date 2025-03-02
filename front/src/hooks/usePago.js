import { useState } from "react";
import { sendPago, getPagos } from "../service/pagoService";

const usePago = () => {
    const [errorPagos, setErrorPagos] = useState("");
    const [listaPagos, setListaPagos] = useState([]);
    const [loadingPagos, setLoadingPagos] = useState(false);

    const realizarPago = async (prestamo, montoPago, metodoPago) => {
        try {
            setErrorPagos('');
            setLoadingPagos(true);
            await sendPago(prestamo, montoPago, metodoPago);
        } catch (error) {
            setErrorPagos("Error al realizar el pago: " + error.message);
        } finally {
            setLoadingPagos(false);
        }
    };

    const cargarPagos = async (idPrestamo) => {
        try {
            setErrorPagos('');
            setLoadingPagos(true);
            const pagos = await getPagos(idPrestamo);
            setListaPagos(pagos);
        } catch (error) {
            setErrorPagos("Error al cargar los pagos: " + error.message);
        } finally {
            setLoadingPagos(false)
        }
    };

    return { errorPagos, listaPagos, loadingPagos, realizarPago, cargarPagos };
};

export default usePago;