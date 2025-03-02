
import logoImage from "../../assets/img/logo-navbar.png";
import "./Navbar.css";

const Navbar = () => {
    return (
        <div className="navbar">
            <div className="logo">
                <a href="/">
                    <img src={logoImage} alt="Logo del Banco" className="logo-navbar" />
                </a>
            </div>
            <div className="nav-links">
                <h2>SEGUROS ESPE</h2>
            </div>
        </div>
    );
};

export default Navbar;
