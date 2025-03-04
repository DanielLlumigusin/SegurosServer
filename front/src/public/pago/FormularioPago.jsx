import React, { useState } from "react";

const FormularioPago = ({prestamo, onRealizarPago }) => {
    const [montoPago, setMontoPago] = useState(0);
    const [metodoPago, setMetodoPago] = useState("");
    const handleSubmit = (e) => {
        e.preventDefault();
        if (!montoPago || !metodoPago) {
            alert("Por favor, complete todos los campos.");
            return;
        }
        onRealizarPago(montoPago, metodoPago);
    };

    console.log(prestamo);
    return (
        <form className="formulario-pago" onSubmit={handleSubmit}>
            <div className="formulario-pago-group">
                <label className="formulario-pago-label">Monto del Pago:</label>
                <input
                    className="formulario-pago-input"
                    type="number"
                    value={montoPago}
                    onChange={(e) => setMontoPago(parseFloat(e.target.value))}
                    required
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
