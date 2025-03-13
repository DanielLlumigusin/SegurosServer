import { useState, useEffect } from "react";
import { getAllPrestamos, aprobarPrestamo, getPrestamoByUsuarioId } from "../service/prestamoService";

const usePrestamo = () => {
    const [loadingPrestamo, setLoadingPrestamo] = useState(false);
    const [errorPrestamo, setErrorPrestamo] = useState('');
    const [listaPrestamos, setListaPrestamos] = useState([]);
    const [mensajePrestamo, setMensajePrestamo] = useState('');

    const getPrestamos = async () => {
        try {
            setLoadingPrestamo(true);
            const response = await getAllPrestamos();
            setListaPrestamos(response)
        } catch (error) {
            console.error("Error al obtener los prestamos", error);
            setErrorPrestamo("Error al cargar los prestamos");
        } finally {
            setLoadingPrestamo(false);
        }
    };

    const findPrestamobyUsuarioId = async (usuarioId) => {
        try {
            setLoadingPrestamo(true);
            setMensajePrestamo('');
            const response = await getPrestamoByUsuarioId(usuarioId);
            return response;
        } catch (error) {
            setErrorPrestamo("Error al no se pudo obtener el o los prestamos.")
        } finally {
            setLoadingPrestamo(false);
        }
    }


    const aprobarPrestamoById = async (prestamo) => {
        try {
            setLoadingPrestamo(true);
            setMensajePrestamo('');
            const response = await aprobarPrestamo(prestamo);
            setMensajePrestamo(response);
            return response;
        } catch (error) {
            setMensajePrestamo('Error al actualizar el prestamo');
            console.error("Error al actualizar el prestamo: ", error);
            throw error;
        } finally {
            setLoadingPrestamo(false);
        }
    }

    useEffect(() => {
        getPrestamos();
    }, []);

    return {
        listaPrestamos,
        errorPrestamo,
        loadingPrestamo,
        mensajePrestamo,
        aprobarPrestamoById,
        findPrestamobyUsuarioId
    }
}

export default usePrestamo;