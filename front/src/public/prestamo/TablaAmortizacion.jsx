import React from "react";

const TablaAmortizacion = ({ tablaAmortizacion }) => {
    return (
        <table className="prestamo-table">
            <thead>
                <tr>
                    <th className="prestamo-table-header">Periodo</th>
                    <th className="prestamo-table-header">Cuota</th>
                    <th className="prestamo-table-header">Interés</th>
                    <th className="prestamo-table-header">Capital</th>
                    <th className="prestamo-table-header">Saldo Pendiente</th>
                </tr>
            </thead>
            <tbody>
                {tablaAmortizacion.map((fila, index) => (
                    <tr key={index} className="prestamo-table-row">
                        <td className="prestamo-table-cell">{fila.periodo}</td>
                        <td className="prestamo-table-cell">${fila.cuota}</td>
                        <td className="prestamo-table-cell">${fila.interes}</td>
                        <td className="prestamo-table-cell">${fila.capital}</td>
                        <td className="prestamo-table-cell">${fila.saldoPendiente}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
};

export default TablaAmortizacion;
