import React, { useState } from "react";

const FormularioPago = ({ onRealizarPago }) => {
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

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>Monto del Pago:</label>
                <input
                    type="number"
                    value={montoPago}
                    onChange={(e) => setMontoPago(parseFloat(e.target.value))}
                    required
                />
            </div>

            <div>
                <label>Método de Pago:</label>
                <select
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

            <button type="submit">Realizar Pago</button>
        </form>
    );
};

export default FormularioPago;