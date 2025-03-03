import { useState, useEffect } from "react";
import { solicitarPrestamo, getPrestamoAprobado, getPrestamoSolicitado } from "../service/prestamoService";

const usePrestamo = () => {
    
    const [errorPrestamo, setErrorPrestamo] = useState('');
    const [loadingPrestamo, setLoadingPrestamo] = useState(false);
    const [prestamoAprobado, setPrestamoAprobado] = useState([]);
    const [prestamoSolicitado, setPrestamoSolicitado] = useState([]);

    const crearPrestamo = async (prestamo) => {
        try {
            setErrorPrestamo('');
            setLoadingPrestamo(true);
            await solicitarPrestamo(prestamo.monto,prestamo.plazo, prestamo.tasaInteres, prestamo.tipoPago)
        } catch (error) {
            setErrorPrestamo("No se puede registrar el prestamo", error);
        }finally{
            setLoadingPrestamo(false);
        }
    }

    const obtenerPrestamoAprobado = async () => {
        try {
            setErrorPrestamo('');
            setLoadingPrestamo(true);
            const data = await getPrestamoAprobado();
            setPrestamoAprobado(data);
        } catch (error) {
            setErrorPrestamo("Error no se puede obtener el prestamo Aprobado");
        }finally{
            setLoadingPrestamo(false);
        }
    }

    const obtenerPrestamoSolicitado = async () => {
        try {
            setErrorPrestamo('');
            setLoadingPrestamo(true);
            const data = await getPrestamoSolicitado();
            setPrestamoSolicitado(data);
        } catch (error) {
            setErrorPrestamo("Error no se puede obtener el prestamos Solicitados");
        }finally{
            setLoadingPrestamo(false);
        }
    }

    useEffect(() => {
        obtenerPrestamoAprobado();
        obtenerPrestamoSolicitado();
    }, []);


    return {crearPrestamo, prestamoAprobado, prestamoSolicitado , errorPrestamo, loadingPrestamo};
};

export default usePrestamo;