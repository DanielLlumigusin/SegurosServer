import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import useAuth from "../../utils/hooks/useAuth";
import logoImage from "../../assets/img/logo-navbar.png";
import "./Navbar.css";

const Navbar = () => {
    const { logout } = useAuth();
    const [token, setToken] = useState(localStorage.getItem("token"));
    const navigate = useNavigate();

    useEffect(() => {
        window.location.reload;
        const handleStorageChange = () => {
            setToken(localStorage.getItem("token"));
        };

        window.addEventListener("storage", handleStorageChange);

        return () => window.removeEventListener("storage", handleStorageChange);
    }, []);

    return (
        <div className="navbar">
            <div className="logo">
                <a href="/">
                    <img src={logoImage} alt="Logo del Banco" className="logo-navbar" />
                </a>
            </div>
            <div className="nav-links">
                {token ? (
                    <>
                        <a href="/perfil">Mi Perfil</a>
                        <a href="/prestamo">Préstamos</a>
                        <a href="/prestamos-solicitados">Mis Préstamos</a>
                        <a href="/pagos">Pagos</a>
                        <button className="btn-logout" onClick={() => { 
                            logout(); 
                            setToken(null); 
                        }}>Cerrar Sesión</button>
                    </>
                ) : (
                    <h2>SEGUROS ESPE</h2>
                )}
            </div>
        </div>
    );
};

export default Navbar;
