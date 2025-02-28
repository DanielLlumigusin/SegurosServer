import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import ApiAxios from "../../../utils/axiosInterceptor";

const useAuth = () => {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [isAuthenticated, setIsAuthenticated] = useState(null);
    const [userRole, setUserRole] = useState(null);
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
        setError("");

        try {
            const response = await ApiAxios.post("/auth/login", { username, password });
            setIsAuthenticated(true);
            setUserRole(response.data.role);
            navigate("/home");
        } catch (error) {
            setError("Error al iniciar sesión: " + (error.response?.data || "Credenciales incorrectas"));
        } finally {
            setLoading(false);
        }
    };

    const logout = async () => {
        try {
            await ApiAxios.post("/auth/logout");
            setIsAuthenticated(false);
            setUserRole(null);
            navigate("/login");
        } catch (error) {
            console.error("Error al cerrar sesión:", error);
        }
    };

    const validateUser = async () => {
        try {
            const response = await ApiAxios.get("/auth/validate-user");
            setIsAuthenticated(true);
            setUserRole(response.data.role);
        } catch (error) {
            setIsAuthenticated(false);
            setUserRole(null);
        }
    };

    const validateAdmin = async () => {
        try {
            const response = await ApiAxios.get("/auth/validate-admin");
            setIsAuthenticated(true);
            setUserRole(response.data.role);
            return response.data.role === "ADMIN";
        } catch (error) {
            return false;
        }
    };

    return { login, logout, validateUser, validateAdmin, isAuthenticated, userRole, loading, error, setError };
};

export default useAuth;
