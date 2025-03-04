import { useNavigate, useSearchParams } from "react-router-dom";
import useRecoveryPassword from "../../hooks/useRecoveryPassword";
import { useEffect, useState } from "react";
import "./ResetPassword.css";
import { Navigate } from "react-router-dom";
const ResetPassword = () => {
    const { verifyTokenResetPassword, formResetPassword, loading, message } = useRecoveryPassword();
    const [searchParams] = useSearchParams();
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [error, setError] = useState("");
    const [passwordStrength, setPasswordStrength] = useState("");
    const [typePassword, setTypePassword] = useState("");
    const token = searchParams.get("token");
    const navigate = useNavigate();

    useEffect(() => {
        if (token) {
            verifyTokenResetPassword(token);
        }
    }, [token]);

    // Función para validar la fortaleza de la contraseña
    const validatePassword = (password) => {
        const minLength = password.length >= 8;
        const hasUppercase = /[A-Z]/.test(password);
        const hasLowercase = /[a-z]/.test(password);
        const hasNumber = /\d/.test(password);
        const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);

        if (!minLength) return "Debe tener al menos 8 caracteres";
        if (!hasUppercase) return "Debe tener al menos una letra mayúscula";
        if (!hasLowercase) return "Debe tener al menos una letra minúscula";
        if (!hasNumber) return "Debe tener al menos un número";
        if (!hasSpecialChar) return "Debe tener al menos un carácter especial";

        return "";
    };

    // Evaluar la seguridad de la contraseña en tiempo real
    const evaluatePasswordStrength = (password) => {
        let score = 0;
        if (password.length >= 8) score += 1;
        if (/[A-Z]/.test(password)) score += 1;
        if (/[a-z]/.test(password)) score += 1;
        if (/\d/.test(password)) score += 1;
        if (/[!@#$%^&*(),.?":{}|<>]/.test(password)) score += 1;

        switch (score) {
            case 1:
            case 2:
                setTypePassword("low")
                return "Débil ❌";
            case 3:
                setTypePassword("medium")
                return "Media ⚠️";
            case 4:
                setTypePassword("strong")
                return "Fuerte ✅";
            case 5:
                setTypePassword("very-strong")
                return "Muy Fuerte 🔥";
            default:
                return "";
        }
    };

    // Manejar el cambio de contraseña
    const handlePasswordChange = (e) => {
        const newPassword = e.target.value;
        setPassword(newPassword);
        setError(validatePassword(newPassword));
        setPasswordStrength(evaluatePasswordStrength(newPassword));
    };

    // Enviar la nueva contraseña
    const sendPassword = async () => {
        if (password !== confirmPassword) {
            setError("Las contraseñas no coinciden");
            return;
        }
        if (error) return;

        await formResetPassword(token, password, confirmPassword);
        setTimeout(() => {
            navigate("/");
        }, 5000);
    };

    return (
        <div className="recovery-background">
            <div className="recovery-container">
                <div className="recovery-title">
                    <h2>Restablecer Contraseña</h2>
                </div>
                <p className="recovery-text">Ingresa tu nueva contraseña</p>

                <label className="recovery-label" htmlFor="password">Contraseña Nueva</label>
                <input
                    type="password"
                    className="recovery-input"
                    placeholder="Nueva Contraseña"
                    required
                    value={password}
                    onChange={handlePasswordChange}
                />
                <p className={`password-strength ${typePassword}`}>Su contraseña es {passwordStrength}</p>

                <label className="recovery-label" htmlFor="confirmPassword">Confirmar Contraseña</label>
                <input
                    type="password"
                    className="recovery-input"
                    placeholder="Confirmar Contraseña"
                    required
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                />
                {password !== confirmPassword && confirmPassword.length > 0 && (
                    <p className="error-message">Las contraseñas no coinciden</p>
                )}

                <button className="recovery-button" onClick={sendPassword} disabled={loading || error}>
                    {loading ? "Cargando..." : "Enviar"}
                </button>

                <span>¿Quieres intentar nuevamente? <a href="/" className="login-link">Iniciar Sesión</a></span>

                {message && <p className="recovery-message">{message}</p>}
            </div>
        </div>
    );
};

export default ResetPassword;
