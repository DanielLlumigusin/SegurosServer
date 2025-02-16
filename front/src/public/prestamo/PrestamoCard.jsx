import React from "react";

const PrestamoCard = ({ prestamo }) => {
    return (
        <div style={{ border: "1px solid #ccc", padding: "10px", margin: "10px", borderRadius: "5px" }}>
            <h3>Préstamo ID: {prestamo.prestamoId}</h3>
            <p>Monto: ${prestamo.montoSolicitado}</p>
            <p>Plazo: {prestamo.plazoAmortizacion} meses</p>
            <p>Tasa de Interés: {prestamo.tasaInteres}%</p>
            <p>Tipo de Pago: {prestamo.tipoPago}</p>
            <p>Estado: {prestamo.estadoPrestamo}</p>
            <p>Fecha de Solicitud: {new Date(prestamo.fechaSolicitud).toLocaleDateString()}</p>
            <p>Usuario: {prestamo.usuario.nombreCompleto}</p>
        </div>
    );
};

export default PrestamoCard;