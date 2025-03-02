import { Navigate, Outlet } from "react-router-dom";
import useAuth from "./hooks/useAuth";
import NavbarSigin from "./components/header/NavbarSigin";

const PrivateRoute = () => {
  const { isAuthenticated, loading } = useAuth();

  if (loading) return <div>Cargando...</div>;

  return (
    <>
      <NavbarSigin />
      
      {isAuthenticated ? <Outlet /> : <Navigate to="/login" replace />}
    </>
  );
};

export default PrivateRoute;
