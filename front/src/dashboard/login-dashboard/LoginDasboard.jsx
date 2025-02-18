import { useState } from 'react';
import axios from 'axios';
import { URLBASE } from '../../utils/tools';
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/header/Navbar';
import './LoginDashboard.css';

const LoginDashboard = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const sendCredential = async () => {
        if (!username || !password) {
            setError("Por favor, complete ambos campos.");
            return;
        }

        setLoading(true);
        setError('');

        try {
            const response = await axios.post(`${URLBASE}/auth/login`, {
                username,
                password
            });

            console.log("Token recibido:", response.data.token);
            localStorage.setItem("token", response.data.token);

            navigate("/dashboard");
        } catch (error) {
            setError("Error al iniciar sesión: " + (error.response?.data?.message || error.message));
        } finally {
            setLoading(false);
        }
    };

    // Función para redirigir a la página de registro
    const irARegistro = () => {
        navigate('/register');
    };

    return (
        <div className="login-container">
            <Navbar />
            <div className="login-field">
                <label htmlFor="username" className="login-label">Correo electrónico</label>
                <input
                    id="username"
                    type="email"
                    placeholder="Username"
                    value={username}
                    onChange={e => setUsername(e.target.value)}
                    className="login-input"
                />
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
            </div>

            {error && <p className="error-message">{error}</p>}

            <button onClick={sendCredential} disabled={loading} className="login-button">
                {loading ? 'Cargando...' : 'Enviar'}
            </button>

            <p className="register-text">
                ¿No tienes cuenta?{' '}
                <span onClick={irARegistro} className="register-link">
                    Crear una cuenta
                </span>
            </p>
        </div>
    );
};

export default LoginDashboard;
