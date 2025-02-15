import { Navigate, Outlet } from "react-router-dom";

const PublicRoute = () => {
  // Verifica si el token está presente en el localStorage
  const token = localStorage.getItem("token");

  // Si hay un token, redirige al dashboard (o cualquier página protegida)
  if (token) {
    return <Navigate to="/dashboard" />;
  }

  // Si no hay token, permite el acceso a las páginas de login o registro
  return <Outlet />;
};

export default PublicRoute;
