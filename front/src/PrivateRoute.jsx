import { Navigate, Outlet } from "react-router-dom";
import { useEffect, useState } from "react";
import useAuth from "./utils/hooks/useAuth";

const PrivateRoute = () => {
  const { validateUser, isAuthenticated, loading } = useAuth();
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const checkAuth = async () => {
      await validateUser();
      setIsLoading(false);
    };

    checkAuth();
  }, []);

  if (loading || isLoading) return <div>Cargando...</div>;

  return isAuthenticated ? <Outlet /> : <Navigate to="/login" replace />;
};

export default PrivateRoute;
