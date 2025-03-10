import { FaCheck } from "react-icons/fa";
import usePrestamo from "../../../hooks/usePrestamo";

const TablePrestamos = ({ data, error }) => {

    const { aprobarPrestamoById } = usePrestamo();

    if (error) {
        return <div className="error-message">{error}</div>;
    }

    if (!data || data.length === 0) {
        return <div className="no-data-message">No hay préstamos disponibles.</div>;
    }

    const handleEditPrestamo = async (prestamo) => {
        await aprobarPrestamoById(prestamo);
    }

    return (
        <table className="table-prestamos">
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
                            <td>{datas.plazoAmortizacion}</td>
                            <td>{datas.tasaInteres}</td>
                            <td>{datas.tipoPago}</td>
                            <td>{datas.estadoPrestamo}</td>
                            <td>{datas.fechaSolicitud}</td>
                            <td><button onClick={(e) => handleEditPrestamo(datas)}><FaCheck /> Aprobar</button></td>
                        </tr>
                    );
                })}
            </tbody>
        </table>
    );
};

export default TablePrestamos;
