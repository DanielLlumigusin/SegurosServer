import React, { useState } from "react";

const FormularioPrestamo = ({ monto, plazo, tipoTasa, tipoPago, tasasInteres, onMontoChange, onPlazoChange, onTipoTasaChange, onTipoPagoChange, onSubmit }) => {
    const [errorMonto, setErrorMonto] = useState("");
    const [errorPlazo, setErrorPlazo] = useState("");

    const handleMontoChange = (e) => {
        const value = parseInt(e.target.value, 10);
        if (isNaN(value)) {
            setErrorMonto("Por favor, ingrese un número válido.");
        } else if (value < 500 || value > 50000) {
            setErrorMonto("El monto debe estar entre $500 y $50,000.");
        } else {
            setErrorMonto("");
        }
        onMontoChange(e);
    };

    const handlePlazoChange = (e) => {
        const value = parseInt(e.target.value, 10);
        if (isNaN(value)) {
            setErrorPlazo("Por favor, ingrese un número válido.");
        } else if (value < 6 || value > 60) {
            setErrorPlazo("El plazo debe estar entre 6 y 60 meses.");
        } else {
            setErrorPlazo("");
        }
        onPlazoChange(e);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (monto < 500 || monto > 50000) {
            setErrorMonto("El monto debe estar entre $500 y $50,000.");
            return;
        }
        if (plazo < 6 || plazo > 60) {
            setErrorPlazo("El plazo debe estar entre 6 y 60 meses.");
            return;
        }
        onSubmit(e);
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>Monto Solicitado:</label>
                <input
                    type="number"
                    value={monto}
                    onChange={handleMontoChange}
                    min="500"
                    max="50000"
                    required
                    className="prestamo-input"
                />
                {errorMonto && <p style={{ color: "red" }}>{errorMonto}</p>}
            </div>

            <div>
                <label>Plazo de Amortización ({tipoPago}):</label>
                <input
                    type="number"
                    value={plazo}
                    onChange={handlePlazoChange}
                    min="6"
                    max="60"
                    required
                    className="prestamo-input"
                />
                {errorPlazo && <p style={{ color: "red" }}>{errorPlazo}</p>}
            </div>

            <div>
                <label>Tasa de Interés:</label>
                <select value={tipoTasa} onChange={onTipoTasaChange} className="prestamo-select">
                    {Object.keys(tasasInteres).map((tipo) => (
                        <option key={tipo} value={tipo}>
                            {tipo} ({tasasInteres[tipo]}%)
                        </option>
                    ))}
                </select>
            </div>

            <div>
                <label>Tipo de Pago:</label>
                <select value={tipoPago} onChange={onTipoPagoChange} className="prestamo-select">
                    <option value="MENSUAL">Mensual</option>
                    <option value="SEMANAL">Semanal</option>
                    <option value="ANUAL">Anual</option>
                </select>
            </div>

            <button type="submit" className="prestamo-button">Solicitar Préstamo</button>
        </form>
    );
};

export default FormularioPrestamo;