import { useState, useEffect } from "react";
import { getAllPrestamos } from "../service/prestamoService";

const usePrestamo = () => {
    const [loadingPrestamo, setLoadingPrestamo] = useState(false);
    const [errorPrestamo, setErrorPrestamo] = useState('');
    const [listaPrestamos, setListaPrestamos] = useState([]);

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

    useEffect(() => {
        getPrestamos();
    }, []);

    return {
        listaPrestamos,
        errorPrestamo,
        loadingPrestamo
    }
}

export default usePrestamo;