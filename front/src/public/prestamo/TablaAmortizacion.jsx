import React from "react";

const TablaAmortizacion = ({ tablaAmortizacion }) => {
    return (
        <table border="1">
            <thead>
                <tr>
                    <th>Periodo</th>
                    <th>Cuota</th>
                    <th>Interés</th>
                    <th>Capital</th>
                    <th>Saldo Pendiente</th>
                </tr>
            </thead>
            <tbody>
                {tablaAmortizacion.map((fila, index) => (
                    <tr key={index}>
                        <td>{fila.periodo}</td>
                        <td>${fila.cuota}</td>
                        <td>${fila.interes}</td>
                        <td>${fila.capital}</td>
                        <td>${fila.saldoPendiente}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
};

export default TablaAmortizacion;