import { Navigate, Outlet } from "react-router-dom";
import { useEffect, useState } from "react";
import useAuth from "./utils/hooks/useAuth";

const PrivateAdminRoute = () => {
  const { validateUser, isAuthenticated, userRole, loading } = useAuth();
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const checkAuth = async () => {
      await validateUser();
      setIsLoading(false);
    };

    checkAuth();
  }, []);

  if (loading || isLoading) return <div>Cargando...</div>;

  return isAuthenticated && userRole === "ADMIN" ? (
    <Outlet />
  ) : (
    <Navigate to="/login-admin" replace />
  );
};

export default PrivateAdminRoute;
