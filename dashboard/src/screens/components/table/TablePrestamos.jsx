import { FaCheck } from "react-icons/fa";

const TablePrestamos = ({ data, error }) => {
    if (error) {
        return <div className="error-message">{error}</div>;
    }

    if (!data || data.length === 0) {
        return <div className="no-data-message">No hay préstamos disponibles.</div>;
    }

    return (
        <table>
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Usuario</th>
                    <th>Monto Solicitado</th>
                    <th>Plazo Amortizado (Meses)</th>
                    <th>Taza Interes</th>
                    <th>Tipo de pago</th>
                    <th>Estado Prestamo</th>
                    <th>Fecha Solicitud</th>
                    <th>Aprobar</th>
                </tr>
            </thead>
            <tbody>
                {data.map((datas) => {
                    return (
                        <tr key={datas.prestamoId}>
                            <td>{datas.prestamoId}</td>
                            <td>{datas.usuario.username}</td>
                            <td>{datas.montoSolicitado}</td>
                            <td>{datas.plazoAmortizado}</td>
                            <td>{datas.tasaInteres}</td>
                            <td>{datas.tipoPago}</td>
                            <td>{datas.estadoPrestamo}</td>
                            <td>{datas.fechaSolicitud}</td>
                            <td><button><FaCheck /> Aprobar</button></td>
                        </tr>
                    );
                })}
            </tbody>
        </table>
    );
};

export default TablePrestamos;
