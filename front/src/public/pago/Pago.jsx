import React, { useEffect } from "react";
import FormularioPago from "./FormularioPago";
import ListaPagos from "./ListaPagos";
import Mensaje from "../components/Mensaje";
import usePago from "../../hooks/usePago";
import usePrestamoAprobado from "../../hooks/usePrestamoAprobado";
import useUsuario from "../../hooks/useUsuario";
import PrestamoCard from "../prestamo/PrestamoCard";
import "./Pago.css";

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
        return <p className="pago-cargando">Cargando...</p>;
    }

    if (!prestamoActivo) {
        return <Mensaje mensaje="No tienes un préstamo activo." />;
    }

    return (
        <div className="pago-container">
            <h1 className="pago-title">Gestión de Pagos</h1>
            <Mensaje mensaje={mensajePrestamo || mensaje} />

            <div className="pago-seccion-container">
                <section className="pago-seccion">
                    <h2 className="pago-subtitle">Préstamo Activo</h2>
                    <PrestamoCard prestamo={prestamoActivo} />
                </section>

                <section className="pago-seccion">
                    <h2 className="pago-subtitle">Realizar un Pago</h2>
                    <FormularioPago prestamo={prestamoActivo} onRealizarPago={handleRealizarPago} />
                </section>

                <section className="pago-seccion">
                    <h2 className="pago-subtitle">Lista de Pagos</h2>
                    <ListaPagos pagos={listaPagos} />
                </section>
            </div>
        </div>
    );
};

export default Pago;
