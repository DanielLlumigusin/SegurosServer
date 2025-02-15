import { useState } from 'react';
import axios from 'axios';
import { URLBASE } from '../../utils/tools';

const LoginDashboard = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

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
        } catch (error) {
            setError("Error al iniciar sesión: " + (error.response?.data?.message || error.message));
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            <div>
                <label htmlFor="username">Correo electrónico</label>
                <input
                    id="username"
                    type="email"
                    placeholder="Username"
                    value={username}
                    onChange={e => setUsername(e.target.value)}
                />
            </div>
            <div>
                <label htmlFor="password">Contraseña</label>
                <input
                    id="password"
                    type="password"
                    placeholder="Contraseña"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                />
            </div>

            {error && <p style={{ color: 'red' }}>{error}</p>}
            <button onClick={sendCredential} disabled={loading}>
                {loading ? 'Cargando...' : 'Enviar'}
            </button>
        </div>
    );
};

export default LoginDashboard;
