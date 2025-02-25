// src/hooks/useAdmin.js
import { useState } from 'react';
import axios from 'axios';
import { URLBASE } from '../tools';
import { useNavigate } from 'react-router-dom';

const useAdmin = () => {
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
            const response = await axios.post(`${URLBASE}/auth/admin/login`, { username, password });
            localStorage.setItem("token", response.data.token);
            window.location.reload;
            navigate("/home");
        } catch (error) {
            setError("Error al iniciar sesión: " + (error.response?.data?.message || error.message));
        } finally {
            setLoading(false);
        }
    };

    const logout = () => {
        localStorage.removeItem("token");
        navigate("/");
    };

    return { login, logout, loading, error, setError };
};

export default useAdmin;
