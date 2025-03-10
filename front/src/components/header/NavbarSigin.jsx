import { useNavigate } from "react-router-dom";
import logoImage from "../../assets/img/logo-navbar.png";
import useAuth from "../../hooks/useAuth";
import "./Navbar.css";

const NavbarSigin = () => {
    const { logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = async () => {
        await logout();
        navigate("/login");
    };

    return (
        <div className="navbar">
            <div className="logo">
                <a href="/">
                    <img src={logoImage} alt="Logo del Banco" className="logo-navbar" />
                </a>
            </div>
            <div className="nav-links">

                <>
                    <a href="/perfil">Mi Perfil</a>
                    <a href="/prestamo">Préstamos</a>
                    <a href="/prestamos-solicitados">Mis Préstamos</a>
                    <a href="/pagos">Pagos</a>
                    <button className="btn-logout" onClick={handleLogout}>Cerrar Sesión</button>
                </>
            </div>
        </div>
    );
};

export default NavbarSigin;
