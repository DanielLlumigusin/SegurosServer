import { useEffect } from "react";
import useUsuario from "../../hooks/useUsuario";
import Spinner from "../components/spinner/Spinner";
import Alert from "../components/alert/Alert";
import TableUsuarios from "../components/table/TableUsuarios";

const GestionUsuarios = () => {
  const { listaUsuarios, error, loadingUsuarios, fetchUsuarios } = useUsuario(); // Cambié "loading" a "loadingUsuarios"

  useEffect(() => {
    fetchUsuarios();
  }, []); // Aunque fetchUsuarios no cambia, se recomienda pasarlo como dependencia

  console.log(listaUsuarios);

  return (
    <section className="dashboard-container">
      <h1 className="dashboard-title">Gestión de Usuarios</h1>

      {error && <Alert message={error} />}

      {loadingUsuarios ? (  // Usar loadingUsuarios en lugar de loading
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
