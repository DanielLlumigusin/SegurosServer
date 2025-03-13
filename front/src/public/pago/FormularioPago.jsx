import React, { useState } from "react";
import usePago from "../../hooks/usePago";

const FormularioPago = ({tablaAmortizacion}) => {
    const {realizarPago} = usePago();
    const [metodoPago, setMetodoPago] = useState("");
    
    const handleSubmit = (e) => {
        e.preventDefault();
        if (!metodoPago) {
            alert("Elija un método de pago.");
            return;
        }
        realizarPago(tablaAmortizacion[0].montoPago, metodoPago);
        window.location.reload();
    };

    return (
        <form className="formulario-pago" onSubmit={handleSubmit}>
            <div className="formulario-pago-group">
                <label className="formulario-pago-label">Monto del Pago:</label>
                <input
                    className="formulario-pago-input"
                    type="number"
                    value={tablaAmortizacion[0].montoPago}
                    readOnly
                />
            </div>

            <div className="formulario-pago-group">
                <label className="formulario-pago-label">Método de Pago:</label>
                <select
                    className="formulario-pago-select"
                    value={metodoPago}
                    onChange={(e) => setMetodoPago(e.target.value)}
                    required
                >
                    <option value="">Seleccione un método</option>
                    <option value="EFECTIVO">Efectivo</option>
                    <option value="TARJETA">Tarjeta</option>
                    <option value="TRANSFERENCIA">Transferencia</option>
                </select>
            </div>

            <button type="submit" className="formulario-pago-button">Realizar Pago</button>
        </form>
    );
};

export default FormularioPago;
