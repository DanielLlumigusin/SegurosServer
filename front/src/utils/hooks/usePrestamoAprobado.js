import { useState, useEffect } from "react";
import prestamoService from "../../service/prestamoService";

const { getPrestamoAprobadoByUsuarioId, getPrestamoSolicitadoByUsuarioId } = prestamoService;

const usePrestamoSolicitado = (usuarioId) => {
    const [prestamoActivo, setPrestamoActivo] = useState(null);
    const [mensaje, setMensaje] = useState("");
    const [cargando, setCargando] = useState(true);

    useEffect(() => {
        const cargarPrestamoActivo = async () => {
            try {
                const prestamosAprobados = await getPrestamoAprobadoByUsuarioId(usuarioId);
                const prestamoActivo = prestamosAprobados.find((prestamo) => prestamo.estadoPrestamo === "APROBADO");
                setPrestamoActivo(prestamoActivo);
            } catch (error) {
                setMensaje("Error al cargar el préstamo activo: " + error.message);
            } finally {
                setCargando(false);
            }
        };

        if (usuarioId) {
            cargarPrestamoActivo();
        }
    }, [usuarioId]);

    return { prestamoActivo, mensaje, cargando };
};

export default usePrestamoSolicitado;