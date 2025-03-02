import "./TableUsuarios.css";
import { FaEdit } from "react-icons/fa";
import { BsTrash } from "react-icons/bs";

const Table = ({ data }) => {
    return (
      <div className="table-container">
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
            {data.map((usuario, index) => (
              <tr key={index}>
                <td>{usuario.usuarioId}</td>
                <td>{usuario.nombreCompleto}</td>
                <td>{usuario.username}</td>
                <td>{usuario.rol}</td>
                <td>{usuario.enabled ? "activo" : "inactivo"}</td>
                <td>
                  <button><FaEdit /></button>
                  <button><BsTrash /></button>

                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    );
  };
  
  export default Table;
  