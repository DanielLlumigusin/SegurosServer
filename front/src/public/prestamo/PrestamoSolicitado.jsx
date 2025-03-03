import React from "react";
import useUsuario from "../../hooks/useUsuario";
import usePrestamo from "../../hooks/usePrestamo";
import ListaPrestamos from "./ListaPrestamos";
import Mensaje from "../components/Mensaje";
import "./PrestamoSolicitado.css";

const PrestamoSolicitado = () => {
    const { usuario, error } = useUsuario();
    const { prestamoAprobado, prestamoSolicitado , mensaje, cargando } = usePrestamo();
    

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
            <ListaPrestamos prestamos={prestamoAprobado} titulo="Préstamos Aprobados" />
            <ListaPrestamos prestamos={prestamoSolicitado} titulo="Préstamos Solicitados" />
        </div>
    );
};

export default PrestamoSolicitado;
