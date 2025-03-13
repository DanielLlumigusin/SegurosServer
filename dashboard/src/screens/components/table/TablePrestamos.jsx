import { FaCheck } from "react-icons/fa";
import usePrestamo from "../../../hooks/usePrestamo";
import "./TablePrestamos.css";
const TablePrestamos = ({ data, error }) => {
    const { aprobarPrestamoById } = usePrestamo();

    if (error) {
        return <div className="error-message">{error}</div>;
    }

    if (!data || data.length === 0) {
        return <div className="no-data-message">No hay préstamos disponibles.</div>;
    }

    const handleEditPrestamo = async (prestamo) => {
        try {
            await aprobarPrestamoById(prestamo);
            alert(`✅ Préstamo #${prestamo.prestamoId} aprobado correctamente.`);
        } catch (err) {
            alert(`❌ Error al aprobar préstamo #${prestamo.prestamoId}.`);
        }
    };

    return (
        <table className="table-prestamos">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Usuario</th>
                    <th>Monto Solicitado</th>
                    <th>Plazo Amortizado (Meses)</th>
                    <th>Tasa Interés</th>
                    <th>Tipo de pago</th>
                    <th>Estado Préstamo</th>
                    <th>Fecha Solicitud</th>
                    <th>Aprobar</th>
                </tr>
            </thead>
            <tbody>
                {data.map((prestamo) => (
                    <tr key={prestamo.prestamoId}>
                        <td>{prestamo.prestamoId}</td>
                        <td>{prestamo.usuario.username}</td>
                        <td>{prestamo.montoSolicitado}</td>
                        <td>{prestamo.plazoAmortizacion}</td>
                        <td>{prestamo.tasaInteres}</td>
                        <td>{prestamo.tipoPago}</td>
                        <td>{prestamo.estadoPrestamo}</td>
                        <td>{prestamo.fechaSolicitud}</td>
                        <td>
                            <button 
                                onClick={() => handleEditPrestamo(prestamo)} 
                                disabled={prestamo.estadoPrestamo === "Aprobado"}
                                className={prestamo.estadoPrestamo === "Aprobado" ? "disabled-button" : ""}
                            >
                                <FaCheck /> {prestamo.estadoPrestamo === "Aprobado" ? "Aprobado" : "Aprobar"}
                            </button>
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>
    );
};

export default TablePrestamos;
