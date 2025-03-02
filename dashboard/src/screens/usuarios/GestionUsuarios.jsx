import { useEffect } from "react";
import useUsuario from "../../hooks/useUsuario";
import Spinner from "../components/spinner/Spinner";
import Alert from "../components/alert/Alert";
import TableUsuarios from "../components/table/TableUsuarios";

const GestionUsuarios = () => {
  const { listaUsuarios, error, loadingUsuarios, fetchUsuarios } = useUsuario(); 

  useEffect(() => {
    fetchUsuarios();
  }, []);
  
  return (
    <section className="dashboard-container">
      <h1 className="dashboard-title">Gestión de Usuarios</h1>

      {error && <Alert message={error} />}

      {loadingUsuarios ? (  
        <Spinner />
      ) : listaUsuarios.length > 0 ? (
        <TableUsuarios data={listaUsuarios} />
      ) : (
        <p className="text-center text-gray-500">No hay usuarios registrados.</p>
      )}
    </section>
  );
};

export default GestionUsuarios;
