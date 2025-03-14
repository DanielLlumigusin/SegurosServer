import React from "react";

const ResumenPrestamo = ({ cuota, tasaInteres, tipoPago }) => {
    return (
        <div>
            <h3>Resumen del Préstamo</h3>
            <p>Cuota {tipoPago.toLowerCase()}: ${cuota.toFixed(2)}</p>
            <p>Tasa de interés: {tasaInteres}%</p>
        </div>
    );
};

export default ResumenPrestamo;