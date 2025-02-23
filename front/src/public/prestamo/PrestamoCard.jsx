import React from "react";

const PrestamoCard = ({ prestamo }) => {
    return (
        <div className="prestamo-card">
            <h3 className="prestamo-card-title">Préstamo ID: {prestamo.prestamoId}</h3>
            <p className="prestamo-card-text">Monto: ${prestamo.montoSolicitado}</p>
            <p className="prestamo-card-text">Plazo: {prestamo.plazoAmortizacion} meses</p>
            <p className="prestamo-card-text">Tasa de Interés: {prestamo.tasaInteres}%</p>
            <p className="prestamo-card-text">Tipo de Pago: {prestamo.tipoPago}</p>
            <p className="prestamo-card-text">Estado: {prestamo.estadoPrestamo}</p>
            <p className="prestamo-card-text">Fecha de Solicitud: {new Date(prestamo.fechaSolicitud).toLocaleDateString()}</p>
            <p className="prestamo-card-text">Usuario: {prestamo.usuario.nombreCompleto}</p>
        </div>
    );
};

export default PrestamoCard;
