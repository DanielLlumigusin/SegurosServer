import { useState } from "react";
import { Link } from "react-router-dom";
import useRecoveryPassword from "../../hooks/useRecoveryPassword";

const RecoveryPassword = () => {
    const [email, setEmail] = useState("");
    const { loading, message, sendEmail } = useRecoveryPassword();
    const [error, setError] = useState("");

    const isValidEmail = (email) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    };

    const handleSubmit = () => {
        if (!email.trim()) {
            setError("Por favor, ingresa tu correo electrónico.");
            return;
        }

        if (!isValidEmail(email)) {
            setError("Por favor, ingresa un correo válido.");
            return;
        }

        setError(""); // Limpiar errores si todo está bien
        sendEmail(email);
    };

    return (
        <div className="recovery-background">
            <div className="recovery-container">
                <div className="recovery-title">
                    <h2>Recuperar Contraseña</h2>
                </div>
                <p className="recovery-text">
                    Ingresa tu correo y te enviaremos un enlace para restablecer tu contraseña.
                </p>

                <label className="recovery-label" htmlFor="email">Correo electrónico</label>
                <input
                    id="email"
                    type="email"
                    placeholder="Tu correo"
                    className="recovery-input"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                {error && <p className="error-message">{error}</p>}

                <button 
                    onClick={handleSubmit} 
                    className="recovery-button" 
                    disabled={loading || !email.trim()}
                >
                    {loading ? "Enviando..." : "Enviar"}
                </button>

                {message && <p className="recovery-message">{message}</p>}

                <p className="login-text">
                    ¿Ya tienes cuenta? <Link to={"/"}><span className="login-link">Iniciar sesión</span></Link>
                </p>
            </div>
        </div>
    );
};

export default RecoveryPassword;
