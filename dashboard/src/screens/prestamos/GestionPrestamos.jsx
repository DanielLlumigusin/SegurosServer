import usePrestamo from "../../hooks/usePrestamo";
import TablePrestamos from "../components/table/TablePrestamos";


const GestionPrestamos = () => {
    const { listaPrestamos,
            errorPrestamo,
            loadingPrestamo } = usePrestamo();
    return (
        <>
            <TablePrestamos data={listaPrestamos} error={errorPrestamo}/>
        </>
    );
}

export default GestionPrestamos;