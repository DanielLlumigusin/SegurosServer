import useLogActividad from "../../hooks/useLogActividad";
import TablaLogsActividad from "../components/table/TableLogsActividad";

const LogActividad = () => {
    const { listaLogActividad,
        errorLogActividad,
        loadingLogActividad } = useLogActividad();
    return (
        <>
        <h1>Historial de Actividad</h1>
            <TablaLogsActividad data={listaLogActividad} error={errorLogActividad}/>
        </>
    );
}


export default LogActividad;