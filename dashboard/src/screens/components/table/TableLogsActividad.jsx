
const TablaLogsActividad = ({ data, error }) => {
    if (error) {
        return <div className="error-message">{error}</div>;
    }

    if (!data || data.length === 0) {
        return <div className="no-data-message">No hay registros del historial del actividad.</div>;
    }

    return (
        <table>
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Usuario</th>
                    <th>Accion</th>
                    <th>Fecha Accion</th>
                    <th>Detalles</th>
                </tr>
            </thead>
            <tbody>
                {data.map((datas) => {
                    return (
                        <tr key={datas.logId}>
                            <td>{datas.logId}</td>
                            <td>{datas.usuario.username}</td>
                            <td>{datas.accion}</td>
                            <td>{datas.fechaAccion}</td>
                            <td>{datas.detalles}</td>
                        </tr>
                    );
                })}
            </tbody>
        </table>
    );
};

export default TablaLogsActividad;
