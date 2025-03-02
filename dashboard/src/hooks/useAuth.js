import { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import ApiAxios from '../utils/axiosInterceptor';

const useAuth = () => {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const login = async (username, password) => {
        if (!username || !password) {
            setError("Por favor, complete ambos campos.");
            return;
        }

        setLoading(true);
        setError('');

        try {
            const response = await ApiAxios.post('/auth/login', { username, password });
            navigate("/home");  // Redirigir a la página principal
        } catch (error) {
            setError("Credenciales Incorrectas. Intentelo de nuevo");
        } finally {
            setLoading(false);
        }
    };

    const logout = async () => {
        try {
            const response = await ApiAxios.post('/auth/logout');
            navigate("/login");
        } catch (error) {
            console.error("Error al cerrar sesión:", error);
        }
    };
    
    return { login, logout, loading, error };
};

export default useAuth;
