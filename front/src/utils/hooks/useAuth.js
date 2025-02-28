import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import ApiAxios from '../axiosInterceptor';

const useAuth = () => {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [isAuthenticated, setIsAuthenticated] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        validateUser();
    }, []);

    const login = async (username, password) => {
        if (!username || !password) {
            setError("Por favor, complete ambos campos.");
            return;
        }

        setLoading(true);
        setError('');

        try {
            await ApiAxios.post('/auth/login', { username, password });
            setIsAuthenticated(true);
            navigate("/home");
        } catch (error) {
            setError("Error al iniciar sesión: " + (error.response?.data?.message || error.message));
        } finally {
            setLoading(false);
        }
    };

    const logout = async () => {
        try {
            await ApiAxios.post('/auth/logout');
            setIsAuthenticated(false);
            navigate("/login");
        } catch (error) {
            console.error("Error al cerrar sesión:", error);
        }
    };

    const validateUser = async () => {
        try {
            await ApiAxios.get('/auth/validate-user');
            setIsAuthenticated(true);
        } catch (error) {
            setIsAuthenticated(false);
        }
    };

    const validateAdmin = async () => {
        try {
            await ApiAxios.get('/auth/validate-admin');
            setIsAuthenticated(true);
        } catch (error) {
            setIsAuthenticated(false);
        }
    };

    return { login, logout, validateUser, validateAdmin, isAuthenticated, loading, error, setError };
};

export default useAuth;
