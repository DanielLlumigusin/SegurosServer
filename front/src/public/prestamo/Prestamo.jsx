import React, { useState } from "react";
import "./Prestamo.css";
import FormularioPrestamo from "./FormularioPrestamo";
import TablaAmortizacion from "./TablaAmortizacion";
import ResumenPrestamo from "./ResumenPrestamo";
import Mensaje from "../components/Mensaje";
import usePrestamo from "../../utils/hooks/usePrestamo";
import useUsuario from "../../utils/hooks/useUsuario";
import prestamoService from "../../service/prestamoService";

const tasasInteres = {
    "Básica": 5.0,
    "Premium": 8.5,
    "VIP": 12.0
};

const Prestamo = () => {
    const [monto, setMonto] = useState(1000);
    const [plazo, setPlazo] = useState(12);
    const [tipoTasa, setTipoTasa] = useState("Básica");
    const [tipoPago, setTipoPago] = useState("MENSUAL");
    const [mensaje, setMensaje] = useState("");

    const { usuario, error } = useUsuario();
    const { cuota, tablaAmortizacion } = usePrestamo(monto, plazo, tasasInteres[tipoTasa], tipoPago);

    const handleSolicitarPrestamo = async (e) => {
        e.preventDefault();

        if (isNaN(monto) || monto < 500 || monto > 50000) {
            setMensaje("El monto debe estar entre $500 y $50,000.");
            return;
        }
        if (isNaN(plazo) || plazo < 6 || plazo > 60) {
            setMensaje("El plazo debe estar entre 6 y 60 meses.");
            return;
        }
        try {
            await prestamoService.solicitarPrestamo(monto, plazo, tasasInteres[tipoTasa], tipoPago, usuario);
            setMensaje("Préstamo solicitado con éxito.");
            setMonto(1000);
            setPlazo(12);
            setTipoTasa("Básica");
            setTipoPago("MENSUAL");
        } catch (error) {
            setMensaje(error.message);
        }
    };

    return (
        <div className="prestamo-container">
            <h1 className="prestamo-title">Solicitar Préstamo</h1>
            <p className="prestamo-note">Nota: Solo se puede hacer un solo préstamo por persona</p>
            <Mensaje mensaje={mensaje} />
            <div className="prestamo-wrapper">
                <div className="prestamo-left">
                    <FormularioPrestamo
                        monto={monto}
                        plazo={plazo}
                        tipoTasa={tipoTasa}
                        tipoPago={tipoPago}
                        tasasInteres={tasasInteres}
                        onMontoChange={(e) => setMonto(parseFloat(e.target.value))}
                        onPlazoChange={(e) => setPlazo(parseInt(e.target.value))}
                        onTipoTasaChange={(e) => setTipoTasa(e.target.value)}
                        onTipoPagoChange={(e) => setTipoPago(e.target.value)}
                        onSubmit={handleSolicitarPrestamo}
                    />
                    <ResumenPrestamo cuota={cuota} tasaInteres={tasasInteres[tipoTasa]} tipoPago={tipoPago} />
                </div>
                <div className="prestamo-right">
                    <h3>Tabla de Amortización</h3>
                    <TablaAmortizacion tablaAmortizacion={tablaAmortizacion} />
                </div>
            </div>
        </div>
    );
};

export default Prestamo;