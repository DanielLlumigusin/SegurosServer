import React, { useEffect } from "react";
import FormularioPago from "./FormularioPago";
import ListaPagos from "./ListaPagos";
import Mensaje from "../components/Mensaje";
import usePago from "../../hooks/usePago";
import usePrestamo from "../../hooks/usePrestamo";
import useUsuario from "../../hooks/useUsuario";
import useTablaAmortizacion from "../../hooks/useTablaAmortizacion";
import PrestamoCard from "../prestamo/PrestamoCard";
import TablaAmortizacionPago from "./TablaAmortizacionPago"
import "./Pago.css";

const Pago = () => {
    const { usuario, error } = useUsuario();
    const { prestamoAprobado, loadingPrestamo, errorPrestamo } = usePrestamo();
    const { mensaje, listaPagos } = usePago();
    const {tablaAmortizacion , loading} = useTablaAmortizacion();    
    const prestamoApro = prestamoAprobado[0];

    if(loading){
        return <Mensaje mensaje={"Cargando Tabla de Amortizacion"} />;
    }

    if (error) {
        return <Mensaje mensaje={error} />;
    }

    if (loadingPrestamo) {
        return <p className="pago-cargando">Cargando...</p>;
    }

    if (!prestamoApro || prestamoApro.length === 0) {
        return <Mensaje mensaje="No tienes un préstamo aprobado." />;
    }

    return (
        <div className="pago-container">
            <h1 className="pago-title">Gestión de Pagos</h1>
            {(errorPrestamo || mensaje) && <Mensaje mensaje={errorPrestamo || mensaje} />}

            <div className="pago-seccion-container">
                
                <section className="pago-seccion">
                    <h2 className="pago-subtitle">Tabla Amortizacion</h2>
                    <TablaAmortizacionPago tablaAmortizacion={tablaAmortizacion} />
                </section>
                
                <section className="pago-seccion">
                    <h2 className="pago-subtitle">Préstamo Activo</h2>
                    <PrestamoCard prestamo={prestamoApro} />
                </section>

                <section className="pago-seccion">
                    <h2 className="pago-subtitle">Realizar un Pago</h2>
                    <FormularioPago tablaAmortizacion={tablaAmortizacion} />
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
