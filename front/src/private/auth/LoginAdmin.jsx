// src/pages/LoginAdmin.js
import { useState } from 'react';
import './LoginAdmin.css';
import useAdmin from '../utils/hooks/useAdmin';

const LoginAdmin = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const { login, loading, error} = useAdmin();

    return (
        <div className="login-background">
            <div className="login-container">
                <div className='login-title'>
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
            </div>
        </div>
    );
};

export default LoginAdmin;
