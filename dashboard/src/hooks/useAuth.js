import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { loginService, logoutService, checkService } from '../service/authService';

const useAuth = () => {
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const navigate = useNavigate();

    const login = async (username, password) => {
        if (!username || !password) {
            setError("Por favor, complete ambos campos.");
            return;
        }
        setLoading(true);
        setError('');

        try {
            await loginService(username, password);
            navigate("/home");
        } catch (error) {
            setError("Credenciales Incorrectas");
        } finally {
            setLoading(false);
        }
    };

    const logout = async () => {
        try {
            await logoutService();
            setIsAuthenticated(false);
            navigate("/login");
        } catch (error) {
            console.error("Error al cerrar sesión:", error);
        }
    };

    const check = async () => {
        setLoading(true);
        setError('');
        try {
            const response = await checkService();
            if (response === "Autenticado") {
                setIsAuthenticated(true);
            } else {
                setIsAuthenticated(false);
            }
        } catch (error) {
            setIsAuthenticated(false);
            setError("Error de autenticación");
            navigate("/");
        } finally {
            setLoading(false);
        }
    };
    

    useEffect(() => {
        check();
    }, []);

    return { login, logout, check, isAuthenticated, loading, error };
};

export default useAuth;
