import { Link } from "react-router-dom";
import { AiFillCreditCard } from "react-icons/ai";
import { FaUsersCog, FaMoneyCheckAlt, FaHistory } from "react-icons/fa";
import { GrTable } from "react-icons/gr";
import { IoExit } from "react-icons/io5";
import useAuth from "../../../hooks/useAuth"; // Asegúrate de importar correctamente el hook
import "./Sidebar.css"; // Importamos los estilos en un archivo separado

const menuItems = [
  { to: "/usuarios", icon: <FaUsersCog className="icon" />, label: "Gestión de Usuarios" },
  { to: "/prestamos", icon: <AiFillCreditCard className="icon" />, label: "Gestión de Préstamos" },
];

const Sidebar = () => {
  const { logout } = useAuth(); // Obtener la función de logout desde el hook

  return (
    <div className="sidebar">
      <ul>
        {menuItems.map((item, index) => (
          <li key={index}>
            <Link to={item.to} className="sidebar-link" aria-label={item.label}>
              {item.icon} <span className="text">{item.label}</span>
            </Link>
          </li>
        ))}
        <li>
          <button 
            onClick={logout} 
            className="sidebar-link logout-btn"
            aria-label="Cerrar sesión"
          >
            <IoExit className="icon exit" /> Salir
          </button>
        </li>
      </ul>
    </div>
  );
};

export default Sidebar;
