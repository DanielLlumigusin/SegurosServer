import useLogActividad from "../../hooks/useLogActividad";
import TablaLogsActividad from "../components/table/TableLogsActividad";

const LogActividad = () => {
    const { listaLogActividad,
        errorLogActividad,
        loadingLogActividad } = useLogActividad();
    return (
        <>
            <TablaLogsActividad data={listaLogActividad} error={errorLogActividad}/>
        </>
    );
}


export default LogActividad;