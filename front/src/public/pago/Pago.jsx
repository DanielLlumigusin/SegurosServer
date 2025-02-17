import React, { useEffect, useState } from "react";
import FormularioPago from "./FormularioPago";
import ListaPagos from "./ListaPagos";
import Mensaje from "../components/Mensaje";
import usePago from "../../utils/hooks/usePago";
import usePrestamoAprobado from "../../utils/hooks/usePrestamoAprobado";
import useUsuario from "../../utils/hooks/useUsuario";
import PrestamoCard from "../prestamo/PrestamoCard";

const Pago = () => {
    const { usuario, error } = useUsuario();
    const { prestamoActivo, mensaje: mensajePrestamo, cargando: cargandoPrestamo } = usePrestamoAprobado(usuario?.usuarioId);
    const { mensaje, listaPagos, realizarPago, cargarPagos } = usePago();

    // Cargar los pagos al montar el componente
    useEffect(() => {
        if (prestamoActivo) {
            cargarPagos(prestamoActivo.prestamoId);
        }
    }, [prestamoActivo, cargarPagos]);

    const handleRealizarPago = async (montoPago, metodoPago) => {
        if (!prestamoActivo) {
            alert("No hay un préstamo activo para realizar el pago.");
            return;
        }
        await realizarPago(prestamoActivo, montoPago, metodoPago);
    };

    if (error) {
        return <Mensaje mensaje={error} />;
    }

    if (cargandoPrestamo) {
        return <p>Cargando...</p>;
    }

    if (!prestamoActivo) {
        return <Mensaje mensaje="No tienes un préstamo activo." />;
    }

    return (
        <div>
            <h1>Gestión de Pagos</h1>

            <Mensaje mensaje={mensajePrestamo || mensaje} />

            <h2>Detalle del Préstamo Activo</h2>
            <PrestamoCard prestamo={prestamoActivo} />

            <h2>Realizar un Pago</h2>
            <FormularioPago onRealizarPago={handleRealizarPago} />

            <h2>Lista de Pagos</h2>
            <ListaPagos pagos={listaPagos} />
        </div>
    );
};

export default Pago;