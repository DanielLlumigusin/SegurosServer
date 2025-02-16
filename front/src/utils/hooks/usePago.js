import { useState } from "react";
import { sendPago, getPagos } from "../../service/pagoService";

const usePago = () => {
    const [mensaje, setMensaje] = useState("");
    const [listaPagos, setListaPagos] = useState([]);

    const realizarPago = async (prestamo, montoPago, metodoPago) => {
        try {
            await sendPago(prestamo, montoPago, metodoPago);
            setMensaje("Pago realizado con éxito.");
            // Actualizar la lista de pagos después de realizar un pago
            const pagos = await getPagos(prestamo);
            setListaPagos(pagos);
        } catch (error) {
            setMensaje("Error al realizar el pago: " + error.message);
        }
    };

    const cargarPagos = async (idPrestamo) => {
        try {
            const pagos = await getPagos(idPrestamo);
            setListaPagos(pagos);
        } catch (error) {
            setMensaje("Error al cargar los pagos: " + error.message);
        }
    };

    return { mensaje, listaPagos, realizarPago, cargarPagos };
};

export default usePago;