import React, { useEffect, useState } from "react";
import FormularioPago from "./FormularioPago";
import ListaPagos from "./ListaPagos";
import Mensaje from "../components/Mensaje";
import usePago from "../../utils/hooks/usePago";

const Pago = ({ idPrestamo }) => {
    const { mensaje, listaPagos, realizarPago, cargarPagos } = usePago();

    // Cargar los pagos al montar el componente
    useEffect(() => {
        cargarPagos(idPrestamo);
    }, [idPrestamo, cargarPagos]);

    const handleRealizarPago = async (montoPago, metodoPago) => {
        await realizarPago(idPrestamo, montoPago, metodoPago);
    };

    return (
        <div>
            <h1>Gestión de Pagos</h1>

            <Mensaje mensaje={mensaje} />

            <h2>Realizar un Pago</h2>
            <FormularioPago onRealizarPago={handleRealizarPago} />

            <h2>Lista de Pagos</h2>
            <ListaPagos pagos={listaPagos} />
        </div>
    );
};

export default Pago;