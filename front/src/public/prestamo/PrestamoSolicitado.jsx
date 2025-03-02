import React from "react";
import useUsuario from "../../hooks/useUsuario";
import usePrestamoSolicitado from "../../hooks/usePrestamoSolicitado";
import ListaPrestamos from "./ListaPrestamos";
import Mensaje from "../components/Mensaje";
import "./PrestamoSolicitado.css";

const PrestamoSolicitado = () => {
    const { usuario, error } = useUsuario();
    const { prestamosAprobados, prestamosSolicitados, mensaje, cargando } = usePrestamoSolicitado(usuario?.usuarioId);

    if (error) {
        return <Mensaje mensaje={error} />;
    }

    if (cargando) {
        return <p className="prestamo-cargando">Cargando...</p>;
    }

    return (
        <div className="prestamo-solicitado-container">
            <h1 className="prestamo-solicitado-title">Mis Préstamos</h1>
            <Mensaje mensaje={mensaje} />
            <ListaPrestamos prestamos={prestamosAprobados} titulo="Préstamos Aprobados" />
            <ListaPrestamos prestamos={prestamosSolicitados} titulo="Préstamos Solicitados" />
        </div>
    );
};

export default PrestamoSolicitado;
