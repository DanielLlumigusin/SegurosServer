import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useAuth from '../../hooks/useAuth';
import logo from "../../assets/img/logo.jpg";
import './Login.css';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errors, setErrors] = useState({});
    const { login, loading, error } = useAuth();
    const navigate = useNavigate();

    const validateForm = () => {
        const newErrors = {};

        // Validación de correo electrónico
        if (!username.trim()) {
            newErrors.username = "El correo es obligatorio.";
        } else if (!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(username)) {
            newErrors.username = "El correo no es válido.";
        }

        // Validación de contraseña
        if (!password) {
            newErrors.password = "La contraseña es obligatoria.";
        } else if (password.length < 8) {
            newErrors.password = "Debe tener al menos 8 caracteres.";
        } else if (!/[A-Z]/.test(password)) {
            newErrors.password = "Debe incluir al menos una mayúscula.";
        } else if (!/[a-z]/.test(password)) {
            newErrors.password = "Debe incluir al menos una minúscula.";
        } else if (!/[0-9]/.test(password)) {
            newErrors.password = "Debe incluir al menos un número.";
        } else if (!/[!@#$%^&*()_+{}\[\]:;<>,.?~\\/-]/.test(password)) {
            newErrors.password = "Debe incluir al menos un carácter especial.";
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleLogin = () => {
        if (validateForm()) {
            login(username, password);
        }
    };

    return (
        <div className="login-background">
            <div className="login-container">
                <div className='login-title'>
                    <img src={logo} className='login-img-form' alt="Logo" />
                    <h2>Iniciar Sesión</h2>
                </div>
                <div className="login-field">
                    <label htmlFor="username" className="login-label">Correo electrónico</label>
                    <input
                        id="username"
                        type="email"
                        placeholder="Correo electrónico"
                        value={username}
                        onChange={e => setUsername(e.target.value)}
                        className="login-input"
                    />
                    {errors.username && <p className="error-message">{errors.username}</p>}
                </div>
                <div className="login-field">
                    <label htmlFor="password" className="login-label">Contraseña</label>
                    <input
                        id="password"
                        type="password"
                        placeholder="Contraseña"
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                        className="login-input"
                    />
                    {errors.password && <p className="error-message">{errors.password}</p>}
                </div>

                {error && <p className="error-message">{error}</p>}

                <button onClick={handleLogin} disabled={loading} className="login-button">
                    {loading ? 'Cargando...' : 'Ingresar'}
                </button>
                
                <p className="forgot-password">
                    ¿Olvidaste tu contraseña?{' '}
                    <span onClick={() => navigate('/recuperar-cuenta')} className="register-link">
                        Recuperar Contraseña
                    </span>
                </p>

                <p className="register-text">
                    ¿No tienes cuenta?{' '}
                    <span onClick={() => navigate('/register')} className="register-link">
                        Crear una cuenta
                    </span>
                </p>
            </div>
        </div>
    );
};

export default Login;
