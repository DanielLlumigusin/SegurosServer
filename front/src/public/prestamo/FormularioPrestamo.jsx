import React from "react";

const FormularioPrestamo = ({ monto, plazo, tipoTasa, tipoPago, tasasInteres, onMontoChange, onPlazoChange, onTipoTasaChange, onTipoPagoChange, onSubmit }) => {
    return (
        <form onSubmit={onSubmit}>
            <div>
                <label>Monto Solicitado:</label>
                <input
                    type="number"
                    value={monto}
                    onChange={onMontoChange}
                    min="500"
                    max="50000"
                    required
                />
            </div>

            <div>
                <label>Plazo de Amortización ({tipoPago}):</label>
                <input
                    type="number"
                    value={plazo}
                    onChange={onPlazoChange}
                    min="6"
                    max="60"
                    required
                />
            </div>

            <div>
                <label>Tasa de Interés:</label>
                <select value={tipoTasa} onChange={onTipoTasaChange}>
                    {Object.keys(tasasInteres).map((tipo) => (
                        <option key={tipo} value={tipo}>
                            {tipo} ({tasasInteres[tipo]}%)
                        </option>
                    ))}
                </select>
            </div>

            <div>
                <label>Tipo de Pago:</label>
                <select value={tipoPago} onChange={onTipoPagoChange}>
                    <option value="MENSUAL">Mensual</option>
                    <option value="SEMANAL">Semanal</option>
                    <option value="ANUAL">Anual</option>
                </select>
            </div>

            <button type="submit">Solicitar Préstamo</button>
        </form>
    );
};

export default FormularioPrestamo;