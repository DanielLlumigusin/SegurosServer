import { Navigate, Outlet } from "react-router-dom";
import ApiAxios from "./utils/axiosInterceptor";
import { useState, useEffect } from "react";

const ProtectedRoute = () => {
    const [loading, setLoading] = useState(true);
    const [isAuthenticated, setIsAuthenticated] = useState(false); 
    const [error, setError] = useState(null); 

    useEffect(() => {
        const checkAuth = async () => {
            try {
                await ApiAxios.get("/auth/check");
                setIsAuthenticated(true);
            } catch (error) {
                setIsAuthenticated(false); 
                setError("No se pudo verificar la autenticación. Intente de nuevo.");
            } finally {
                setLoading(false); 
            }
        };
        checkAuth(); 
    }, []);

    if (loading) return <p>Cargando...</p>;

    if (error) return <p>{error}</p>;

    return isAuthenticated ? <Outlet /> : <Navigate to="/" replace />;
};

export default ProtectedRoute;
