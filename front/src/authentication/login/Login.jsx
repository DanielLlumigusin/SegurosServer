// src/pages/Login.js
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useAuth from '../../utils/hooks/useAuth';
import logo from "../../assets/img/logo.jpg";
import './Login.css';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const { login, loading, error, setError } = useAuth();
    const navigate = useNavigate();

    return (
        <div className="login-background">
            <div className="login-container">
                <div className='login-title'>
                    <img src={logo} className='login-img-form' />
                    <h2>Iniciar Sesión</h2>
                </div>
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

                <button onClick={() => login(username, password)} disabled={loading} className="login-button">
                    {loading ? 'Cargando...' : 'Ingresar'}
                </button>
                
                <span className="forgot-password">
                    ¿Olvidaste tu contraseña?{' '}
                    <span onClick={() => navigate('/recuperar-cuenta')} className="register-link">
                        Recuperar Contraseña
                    </span>
                </span>

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
