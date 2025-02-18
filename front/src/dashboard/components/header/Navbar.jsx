import logoImage from '../../img/logo.jpg';  // Ajusta la ruta según donde esté el archivo
import './Navbar.css'; // Asume que tienes estilos específicos para el Navbar

const Navbar = () => {
    return (
        <div className="navbar">
            <div className="logo">
                <a href="#">
                    <img src={logoImage} alt="Logo del Banco" style={{ height: '40px' }} />
                </a>
            </div>


            <div>
                <a href="#">PERSONAS</a>
                <a href="#">EMPRESAS</a>
                <a href="#">PROMOCIONES</a>
                <a href="#">NOSOTROS</a>
            </div>
        </div>
    );
};

export default Navbar;
