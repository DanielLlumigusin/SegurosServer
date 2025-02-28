import './App.css';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Register from './authentication/register/Register.jsx';
import PrivateRoute from './PrivateRoute';
import PrivateAdminRoute from './PrivateAdminRoute';
import PublicRoute from './PublicRoute';
import Prestamo from './public/prestamo/Prestamo.jsx';
import NotFound from './public/notFound/NotFound.jsx';
import Perfil from './public/perfil/Perfil.jsx';
import Pago from './public/pago/Pago.jsx';
import PrestamoSolicitado from './public/prestamo/PrestamoSolicitado.jsx';
import Login from './authentication/login/Login.jsx';
import Navbar from './components/header/Navbar.jsx';
import Home from './public/home/Home.jsx';
import LoginAdmin from './private/auth/LoginAdmin.jsx';
import Dashboard from './private/dashboard/Dashboard.jsx';
import GestionUsuarios from './private/usuarios/GestionUsuarios.jsx';
import GestionPrestamos from './private/prestamos/GestionPrestamos.jsx';
import GestionPagos from './private/pagos/GestionPagos.jsx';
function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        {/* Rutas públicas protegidas por PublicRoute */}
        <Route element={<PublicRoute />}>
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<Login />} />
          <Route path='/register' element={<Register />} />
          <Route path='/login-admin' element={<LoginAdmin />} />
        </Route>

        {/* Rutas privadas protegidas por PrivateRoute */}
        <Route element={<PrivateRoute />}>
          <Route path='/home' element={<Home />} />
          <Route path='/perfil' element={<Perfil />} />
          <Route path='/prestamo' element={<Prestamo />} />
          <Route path='/prestamos-solicitados' element={<PrestamoSolicitado />} />
          <Route path='/pagos' element={<Pago />} />
        </Route>

        {/* Rutas privadas protegidas por PrivateAdminRoute */}
        <Route element={<PrivateAdminRoute />}>
          <Route path='/dashboard' element={<Dashboard />} />
          <Route path='/gestion-usuarios' element={<GestionUsuarios />} />
          <Route path='/gestion-prestamo' element={<GestionPrestamos />} />
          <Route path='/gestion-pagos' element={<GestionPagos/>} />
        </Route>
        <Route path='*' element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
