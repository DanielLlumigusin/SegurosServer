import { useState } from 'react';
import axios from 'axios';
import { URLBASE } from '../../utils/tools';
import { useNavigate } from 'react-router-dom';

const LoginDashboard = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const sendCredential = async () => {
        // Verificar que ambos campos estén completos
        if (!username || !password) {
            setError("Por favor, complete ambos campos.");
            return;
        }

        setLoading(true);
        setError(''); 
        
        try {
            // Hacer la solicitud de login
            const response = await axios.post(`${URLBASE}/auth/login`, {
                username,
                password
            });

            // Guardar el token en el almacenamiento local
            console.log("Token recibido:", response.data.token);
            localStorage.setItem("token", response.data.token);

            // Redirigir al usuario al dashboard solo si el login fue exitoso
            navigate("/dashboard");

        } catch (error) {
            // Mostrar un error en caso de que algo falle en la autenticación
            setError("Error al iniciar sesión: " + (error.response?.data?.message || error.message));
        } finally {
            // Detener el loading, independientemente de si fue exitoso o no
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
