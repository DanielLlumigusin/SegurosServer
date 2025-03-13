import React from "react";

const ListaPagos = ({ pagos }) => {
    if (!pagos || pagos.length === 0) {
        return <p className="lista-pagos-sin-datos">No hay pagos registrados.</p>;
    }

    return (
        <table className="lista-pagos-table">
            <thead>
                <tr>
                    <th className="lista-pagos-header">ID</th>
                    <th className="lista-pagos-header">Monto</th>
                    <th className="lista-pagos-header">Método</th>
                    <th className="lista-pagos-header">Fecha</th>
                    <th className="lista-pagos-header">Estado</th>
                </tr>
            </thead>
            <tbody>
                {pagos.map((pago) => (
                    <tr key={pago.id} className="lista-pagos-row">
                        <td className="lista-pagos-cell">{pago.pagoId}</td>
                        <td className="lista-pagos-cell">${pago.montoPago}</td>
                        <td className="lista-pagos-cell">{pago.metodoPago}</td>
                        <td className="lista-pagos-cell">{new Date(pago.fechaPago).toLocaleDateString()}</td>
                        <td className="lista-pagos-cell">{pago.estadoPago}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
};

export default ListaPagos;
