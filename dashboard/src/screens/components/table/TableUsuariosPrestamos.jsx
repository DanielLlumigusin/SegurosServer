import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import usePrestamo from "../../../hooks/usePrestamo";
import { useNavigate } from "react-router-dom";
const TableUsuariosPrestamos = () => {
    const navigate = useNavigate();
    const { usuarioId } = useParams();
    const [listaPrestamos, setListaPrestamos] = useState([]);
    const {
        findPrestamobyUsuarioId, 
        errorPrestamo, 
        loadingPrestamo,
        mensajePrestamo
    } = usePrestamo();

    // Llamar a la API solo cuando usuarioId cambie
    useEffect(() => {
        const fetchPrestamos = async () => {
            if (usuarioId) {
                const prestamos = await findPrestamobyUsuarioId(usuarioId);
                setListaPrestamos(prestamos || []); // Evitar valores nulos
            }
        };
        fetchPrestamos();
    }, [usuarioId]);

    // Función que maneja la acción del botón "Ver Pagos"
    const handleVerPagos = (prestamoId) => {
        navigate(`/usuario/prestamo/pagos/${prestamoId}`);
    };

    return (
        <div>
            {loadingPrestamo ? (
                <p>Cargando préstamos...</p>
            ) : errorPrestamo ? (
                <p>Error: {mensajePrestamo}</p>
            ) : (
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
                            <th>Acción</th>
                        </tr>
                    </thead>
                    <tbody>
                        {listaPrestamos.length > 0 ? (
                            listaPrestamos.map((datas) => (
                                <tr key={datas.prestamoId}>
                                    <td>{datas.prestamoId}</td>
                                    <td>{datas.usuario?.username ?? "N/A"}</td>
                                    <td>{datas.montoSolicitado}</td>
                                    <td>{datas.plazoAmortizacion}</td>
                                    <td>{datas.tasaInteres}</td>
                                    <td>{datas.tipoPago}</td>
                                    <td>{datas.estadoPrestamo}</td>
                                    <td>{datas.fechaSolicitud}</td>
                                    <td>
                                        <button
                                            onClick={() => handleVerPagos(datas.prestamoId)}
                                            disabled={datas.estadoPrestamo !== "APROBADO"}
                                        >
                                            Ver Pagos
                                        </button>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan="9">No hay préstamos disponibles</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default TableUsuariosPrestamos;
