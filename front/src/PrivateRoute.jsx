import { Navigate, Outlet } from "react-router-dom";

const PrivateRoute = () => {
  // Verifica si el token está presente en el localStorage
  const token = localStorage.getItem("token");

  // Si no hay token, redirige a la página de login
  if (!token) {
    window.location.reload;
    return <Navigate to="/" />;
  }

  // Si hay token, permite el acceso a las rutas privadas
  return <Outlet />;
};

export default PrivateRoute;
