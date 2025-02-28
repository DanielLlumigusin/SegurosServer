import { useEffect, useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import useAuth from "../../utils/hooks/useAuth";
import logoImage from "../../assets/img/logo-navbar.png";
import "./Navbar.css";

const Navbar = () => {
    const { logout, validateUser, isAuthenticated, userRole } = useAuth();
    const navigate = useNavigate();
    const [loading, setLoading] = useState(true);
    const hasValidated = useRef(false); // Evita múltiples ejecuciones de `validateUser`

    useEffect(() => {
        if (!hasValidated.current) {
            hasValidated.current = true;
            validateUser().then(() => setLoading(false));
        }
    }, []);

    const handleLogout = async () => {
        await logout();
        navigate("/login");
    };

    if (loading) {
        return <div className="navbar"><h2>Cargando...</h2></div>;
    }

    return (
        <div className="navbar">
            <div className="logo">
                <a href="/">
                    <img src={logoImage} alt="Logo del Banco" className="logo-navbar" />
                </a>
            </div>
            <div className="nav-links">
                {isAuthenticated ? (
                    <>
                        <a href="/perfil">Mi Perfil</a>
                        <a href="/prestamo">Préstamos</a>
                        <a href="/prestamos-solicitados">Mis Préstamos</a>
                        <a href="/pagos">Pagos</a>
                        {userRole === "ADMIN" && <a href="/admin">Panel Admin</a>}
                        <button className="btn-logout" onClick={handleLogout}>Cerrar Sesión</button>
                    </>
                ) : (
                    <h2>SEGUROS ESPE</h2>
                )}
            </div>
        </div>
    );
};

export default Navbar;
