import React from "react";
import useUsuario from "../../utils/hooks/useUsuario";
import usePrestamoSolicitado from "../../utils/hooks/usePrestamoSolicitado";
import ListaPrestamos from "./ListaPrestamos";
import Mensaje from "../components/Mensaje";

const PrestamoSolicitado = () => {
    const { usuario, error } = useUsuario();
    const { prestamosAprobados, prestamosSolicitados, mensaje, cargando } = usePrestamoSolicitado(usuario?.usuarioId);

    if (error) {
        return <Mensaje mensaje={error} />;
    }

    if (cargando) {
        return <p>Cargando...</p>;
    }

    return (
        <div>
            <h1>Mis Préstamos</h1>

            <Mensaje mensaje={mensaje} />

            <ListaPrestamos prestamos={prestamosAprobados} titulo="Préstamos Aprobados" />
            <ListaPrestamos prestamos={prestamosSolicitados} titulo="Préstamos Solicitados" />
        </div>
    );
};

export default PrestamoSolicitado;