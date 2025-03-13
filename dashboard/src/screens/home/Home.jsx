import "./Home.css";
import seguridadImg from "../../../public/seguridad.png";
import rapidezImg from "../../../public/rapidez.png";
import atencionImg from "../../../public/atencion.png";

const Home = () => {
    return (
        <div className="home-container">
            <header className="hero-section">
                <h1>Bienvenido a Seguros ESPE</h1>
                <p>Tu seguridad y confianza son nuestra prioridad.</p>
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
                <p>&copy; 2025 Seguros ESPE. Todos los derechos reservados.</p>
            </footer>
        </div>
    );
};

export default Home;
