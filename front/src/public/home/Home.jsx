import "./Home.css";
import seguridadImg from "../../assets/img/seguridad.png";
import rapidezImg from "../../assets/img/rapidez.png";
import atencionImg from "../../assets/img/atencion.png";

const Home = () => {
    return (
        <div className="home-container">
            <header className="hero-section">
                <h1>Bienvenido a Banco Seguro</h1>
                <p>Tu seguridad y confianza son nuestra prioridad.</p>
                <a href="/prestamo" className="btn-primary">Pide un Préstamo</a>
            </header>

            <section className="features">
                <div className="feature-card">
                    <img src={seguridadImg} alt="Seguridad" style={{width: "150px", height: "auto"}}/>
                    <h3>Seguridad</h3>
                    <p>Protegemos tus datos y transacciones con la mejor tecnología.</p>
                </div>
                <div className="feature-card">
                    <img src={rapidezImg} alt="Rapidez" style={{width: "150px", height: "auto"}}/>
                    <h3>Rapidez</h3>
                    <p>Transacciones rápidas y eficientes en cualquier momento.</p>
                </div>
                <div className="feature-card">
                    <img src={atencionImg} alt="Atencion" style={{width: "150px", height: "auto"}}/>
                    <h3>Atención 24/7</h3>
                    <p>Nuestro equipo está disponible para ti en todo momento.</p>
                </div>
            </section>

            <footer className="footer">
                <p>&copy; 2025 Banco Seguro. Todos los derechos reservados.</p>
            </footer>
        </div>
    );
};

export default Home;
