import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import usePago from "../../../hooks/usePago";

const TablePrestamosPago = () => {
    const { prestamoId } = useParams();
    const { obtenerPagoByPrestamoId, listPago, loadingPago, errorPago } = usePago();
    const [pagos, setPagos] = useState([]);

    // Obtener pagos cuando el prestamoId cambie
    useEffect(() => {
        const fetchPagos = async () => {
            if (prestamoId) {
                try {
                    await obtenerPagoByPrestamoId(prestamoId);
                } catch (err) {
                    console.error("Error al obtener pagos:", err);
                }
            }
        };

        fetchPagos();
    }, [prestamoId]); 

    useEffect(() => {
        if (listPago.length > 0) {
            setPagos(listPago);
        }
    }, [listPago]); 

    return (
        <div>
            {loadingPago ? (
                <p>Cargando pagos...</p>
            ) : errorPago ? (
                <p>{errorPago}</p>
            ) : (
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
                        {pagos.length > 0 ? (
                            pagos.map((pago) => (
                                <tr key={pago.pagoId} className="lista-pagos-row">
                                    <td className="lista-pagos-cell">{pago.pagoId}</td>
                                    <td className="lista-pagos-cell">${pago.montoPago}</td>
                                    <td className="lista-pagos-cell">{pago.metodoPago}</td>
                                    <td className="lista-pagos-cell">
                                        {new Date(pago.fechaPago).toLocaleDateString()}
                                    </td>
                                    <td className="lista-pagos-cell">{pago.estadoPago}</td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan="5">No hay pagos disponibles</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default TablePrestamosPago;
