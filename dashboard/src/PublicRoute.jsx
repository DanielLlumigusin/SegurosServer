import { Navigate, Outlet } from "react-router-dom";
import useAuth from "./hooks/useAuth";
// import Navbar from "./components/header/Navbar";

const PublicRoute = () => {
  const { isAuthenticated, loading } = useAuth();

  if (loading) return <div>Cargando...</div>;

  return (
    <>
      {/* <Navbar /> */}
      
      {isAuthenticated ? <Navigate to="/home" replace /> : <Outlet />}
    </>
  );
};

export default PublicRoute;