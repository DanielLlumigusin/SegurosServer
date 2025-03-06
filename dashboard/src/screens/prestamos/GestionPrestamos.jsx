import usePrestamo from "../../hooks/usePrestamo";
import TablePrestamos from "../components/table/TablePrestamos";


const GestionPrestamos = () => {
    const { listaPrestamos,
            errorPrestamo,
            loadingPrestamo } = usePrestamo();

    if(loadingPrestamo){
        return <h2>Cargando Prestamos espere un momento...</h2>
    }
            
    return (
        <>
            <TablePrestamos data={listaPrestamos} error={errorPrestamo}/>
        </>
    );
}

export default GestionPrestamos;