import "./TableUsuarios.css";
import { FaEdit } from "react-icons/fa";
import { BsTrash } from "react-icons/bs";
import { useState } from "react";
import useUsuario from "../../../hooks/useUsuario";
import {useNavigate} from "react-router-dom";
const Table = ({ data }) => {
  const navigate = useNavigate(); // Hook para la navegación
  const { eliminarUsuario } = useUsuario();
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState(null);

  const handleOnDelete = async (usuarioId) => {
    const confirmDelete = window.confirm("¿Estás seguro de que deseas eliminar este usuario?");
    if (!confirmDelete) return;

    try {
      setLoading(true);
      setError(null);
      await eliminarUsuario(usuarioId);
      setSuccessMessage("Usuario eliminado con éxito.");
    } catch (error) {
      setError("Error al eliminar el usuario. Por favor, intenta de nuevo.");
    } finally {
      setLoading(false);
    }
  };

  const handlePrestamoByUsuarioId = (usuarioId) => {
    navigate(`/usuario/prestamo/${usuarioId}`);
  };  

  return (
    <div className="table-container">
      {error && <p className="error-message">{error}</p>}
      {successMessage && <p className="success-message">{successMessage}</p>}

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
                <button 
                  onClick={() => handlePrestamoByUsuarioId(usuario.usuarioId)}
                >
                  Ver Préstamos
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

