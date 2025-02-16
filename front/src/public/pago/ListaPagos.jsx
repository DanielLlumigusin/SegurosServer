import React from "react";

const ListaPagos = ({ pagos }) => {
    if (!pagos || pagos.length === 0) {
        return <p>No hay pagos registrados.</p>;
    }

    return (
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Monto</th>
                    <th>Método</th>
                    <th>Fecha</th>
                </tr>
            </thead>
            <tbody>
                {pagos.map((pago) => (
                    <tr key={pago.id}>
                        <td>{pago.id}</td>
                        <td>${pago.montoPago}</td>
                        <td>{pago.metodoPago}</td>
                        <td>{new Date(pago.fechaPago).toLocaleDateString()}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
};

export default ListaPagos;