import React, { useEffect } from "react";
import FormularioPago from "./FormularioPago";
import ListaPagos from "./ListaPagos";
import Mensaje from "../components/Mensaje";
import usePago from "../../hooks/usePago";
import usePrestamo from "../../hooks/usePrestamo";
import useUsuario from "../../hooks/useUsuario";
import PrestamoCard from "../prestamo/PrestamoCard";
import "./Pago.css";

const Pago = () => {
    const { usuario, error } = useUsuario();
    const { prestamoAprobado, loadingPrestamo, errorPrestamo } = usePrestamo();
    const { mensaje, listaPagos, realizarPago } = usePago();

    const handleRealizarPago = async (montoPago, metodoPago) => {
        if (!prestamoAprobado || !prestamoAprobado.prestamoId) {
            alert("No hay un préstamo activo para realizar el pago.");
            return;
        }
        
        if (montoPago <= 0) {
            alert("El monto de pago debe ser mayor a 0.");
            return;
        }

        await realizarPago(prestamoAprobado, montoPago, metodoPago);
    };

    if (error) {
        return <Mensaje mensaje={error} />;
    }

    if (loadingPrestamo) {
        return <p className="pago-cargando">Cargando...</p>;
    }

    if (!prestamoAprobado || prestamoAprobado.length === 0) {
        return <Mensaje mensaje="No tienes un préstamo aprobado." />;
    }

    return (
        <div className="pago-container">
            <h1 className="pago-title">Gestión de Pagos</h1>
            {(errorPrestamo || mensaje) && <Mensaje mensaje={errorPrestamo || mensaje} />}

            <div className="pago-seccion-container">
                <section className="pago-seccion">
                    <h2 className="pago-subtitle">Préstamo Activo</h2>
                    <PrestamoCard prestamo={prestamoAprobado} />
                </section>

                <section className="pago-seccion">
                    <h2 className="pago-subtitle">Realizar un Pago</h2>
                    <FormularioPago prestamo={prestamoAprobado} onRealizarPago={handleRealizarPago} />
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
