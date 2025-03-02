import { useState, useEffect } from "react";
import { solicitarPrestamo, getPrestamoAprobadoByUsuarioId, getPrestamoSolicitadoByUsuarioId } from "../service/prestamoService";

const usePrestamo = () => {
    
    const [errorPrestamo, setErrorPrestamo] = useState('');
    const [loadingPrestamo, setLoadingPrestamo] = useState(false);
    const [prestamoAprobado, setPrestamoAprobado] = useState([]);
    const [prestamoSolicitado, setPrestamoSolicitado] = useState([]);

    const crearPrestamo = async (prestamo) => {
        try {
            setErrorPrestamo('');
            setLoadingPrestamo(true);
            await solicitarPrestamo(prestamo.monto,prestamo.plazo, prestamo.tasaInteres, prestamo.tipoPago, prestamo.usuario)
        } catch (error) {
            setErrorPrestamo("No se puede registrar el prestamo");
        }finally{
            setLoadingPrestamo(false);
        }
    }

    const obtenerPrestamoAprobado = async (usuarioId) => {
        try {
            setErrorPrestamo('');
            setLoadingPrestamo(true);
            const data = await getPrestamoAprobadoByUsuarioId(usuarioId);
            setPrestamoAprobado(data);
        } catch (error) {
            setErrorPrestamo("Error no se puede obtener el prestamo Aprobado");
        }finally{
            setLoadingPrestamo(false);
        }
    }

    const obtenerPrestamoSolicitado = async (usuarioId) => {
        try {
            setErrorPrestamo('');
            setLoadingPrestamo(true);
            const data = await getPrestamoSolicitadoByUsuarioId(usuarioId);
            setPrestamoSolicitado(data);
        } catch (error) {
            setErrorPrestamo("Error no se puede obtener el prestamos Solicitados");
        }finally{
            setLoadingPrestamo(false);
        }
    }

    return {crearPrestamo, obtenerPrestamoAprobado, obtenerPrestamoSolicitado, prestamoAprobado, prestamoSolicitado , errorPrestamo, loadingPrestamo};
};

export default usePrestamo;