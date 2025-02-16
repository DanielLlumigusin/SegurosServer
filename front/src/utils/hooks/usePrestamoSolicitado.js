import { useState, useEffect } from "react";
import prestamoService from "../../service/prestamoService";

const { getPrestamoAprobadoByUsuarioId, getPrestamoSolicitadoByUsuarioId } = prestamoService;

const usePrestamoSolicitado = (usuarioId) => {
    const [prestamosAprobados, setPrestamosAprobados] = useState([]);
    const [prestamosSolicitados, setPrestamosSolicitados] = useState([]);
    const [mensaje, setMensaje] = useState("");
    const [cargando, setCargando] = useState(true);

    useEffect(() => {
        const cargarPrestamos = async () => {
            try {
                const aprobados = await getPrestamoAprobadoByUsuarioId(usuarioId);
                const solicitados = await getPrestamoSolicitadoByUsuarioId(usuarioId);
                setPrestamosAprobados(aprobados);
                setPrestamosSolicitados(solicitados);
            } catch (error) {
                setMensaje("Error al cargar los préstamos: " + error.message);
            } finally {
                setCargando(false);
            }
        };

        if (usuarioId) {
            cargarPrestamos();
        }
    }, [usuarioId]);

    return { prestamosAprobados, prestamosSolicitados, mensaje, cargando };
};

export default usePrestamoSolicitado;