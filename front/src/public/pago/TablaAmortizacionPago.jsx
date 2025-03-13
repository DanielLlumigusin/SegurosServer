import React from "react";

const TablaAmortizacionPago = ({ tablaAmortizacion }) => {
    return (
        <table className="prestamo-table">
            <thead>
                <tr>
                    <th className="prestamo-table-header">Número Pago</th>
                    <th className="prestamo-table-header">Fecha Pago</th>
                    <th className="prestamo-table-header">Cuota</th>
                    <th className="prestamo-table-header">Interés</th>
                    <th className="prestamo-table-header">Capital</th>
                    <th className="prestamo-table-header">Saldo Pendiente</th>
                </tr>
            </thead>
            <tbody>
                {tablaAmortizacion.map((fila, index) => (
                    <tr key={index} className="prestamo-table-row">
                        <td className="prestamo-table-cell">{fila.numeroPago}</td> 
                        <td className="prestamo-table-cell">{fila.fechaPago}</td>
                        <td className="prestamo-table-cell">${fila.montoPago}</td>
                        <td className="prestamo-table-cell">${fila.interes}</td>
                        <td className="prestamo-table-cell">${fila.capital}</td>
                        <td className="prestamo-table-cell">${fila.saldoRestante}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
};

export default TablaAmortizacionPago;
