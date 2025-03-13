import { useEffect, useState } from "react";
import { sendPago, getPagos } from "../service/pagoService";

const usePago = () => {
    const [errorPagos, setErrorPagos] = useState("");
    const [listaPagos, setListaPagos] = useState([]);
    const [loadingPagos, setLoadingPagos] = useState(false);
    const [mensaje, setMensaje] = useState("");

    const realizarPago = async (montoPago, metodoPago) => {
        try {
            console.log(metodoPago, montoPago);
            setErrorPagos('');
            setLoadingPagos(true);
            await sendPago( montoPago, metodoPago);
            setMensaje('Pago realizado espera la aprobacion');
        } catch (error) {
            setErrorPagos("Error al realizar el pago: " + error.message);
        } finally {
            setLoadingPagos(false);
        }
    };

    const cargarPagos = async () => {
        try {
            setErrorPagos('');
            setLoadingPagos(true);
            const pagos = await getPagos();
    
            if (pagos.message) {
                setMensaje(pagos.message);  
            } else {
                setListaPagos(pagos);  
            }
            
        } catch (error) {
            setErrorPagos("Error al cargar los pagos: " + error.message);  
        } finally {
            setLoadingPagos(false); 
        }
    };
    

    useEffect(() => {
        cargarPagos();
    }, []);

    return { errorPagos, listaPagos, loadingPagos, mensaje, realizarPago, cargarPagos };
};

export default usePago;