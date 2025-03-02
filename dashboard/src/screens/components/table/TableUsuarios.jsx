import "./TableUsuarios.css";
import { FaEdit } from "react-icons/fa";
import { BsTrash } from "react-icons/bs";
import { useState } from "react";
import useUsuario from "../../../hooks/useUsuario";

const Table = ({ data }) => {
  const { eliminarUsuario } = useUsuario();
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState(null);  // Para mostrar el mensaje de éxito

  const handleOnDelete = async (usuarioId) => {
    const confirmDelete = window.confirm("¿Estás seguro de que deseas eliminar este usuario?");
    
    if (!confirmDelete) return;

    try {
      setLoading(true);
      setError(null);  // Limpiar error previo
      await eliminarUsuario(usuarioId);
      setSuccessMessage("Usuario eliminado con éxito.");  // Mensaje de éxito
    } catch (error) {
      setError("Error al eliminar el usuario. Por favor, intenta de nuevo.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="table-container">
      {error && <p className="error-message">{error}</p>}
      {successMessage && <p className="success-message">{successMessage}</p>} {/* Mensaje de éxito */}

      <table className="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre Completo</th>
            <th>Username</th>
            <th>Rol</th>
            <th>Estado</th>
            <th>Opciones</th>
          </tr>
        </thead>
        <tbody>
          {data.map((usuario) => (
            <tr key={usuario.usuarioId}>
              <td>{usuario.usuarioId}</td>
              <td>{usuario.nombreCompleto}</td>
              <td>{usuario.username}</td>
              <td>{usuario.rol}</td>
              <td>{usuario.enabled ? "activo" : "inactivo"}</td>
              <td>
                <button title="Editar usuario"><FaEdit /></button>
                <button 
                  onClick={() => handleOnDelete(usuario.usuarioId)} 
                  disabled={loading} 
                  title="Eliminar usuario"
                >
                  {loading ? <span className="loading-spinner">...</span> : <BsTrash />}
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Table;
