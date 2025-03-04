import './App.css';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Register from './authentication/register/Register.jsx';
import PrivateRoute from './PrivateRoute';
import PublicRoute from './PublicRoute';
import Prestamo from './public/prestamo/Prestamo.jsx';
import NotFound from './public/notFound/NotFound.jsx';
import Perfil from './public/perfil/Perfil.jsx';
import Pago from './public/pago/Pago.jsx';
import PrestamoSolicitado from './public/prestamo/PrestamoSolicitado.jsx';
import Login from './authentication/login/Login.jsx';
import Home from './public/home/Home.jsx';
import RecoveryPassword from './public/recoveryPassword/RecoveryPassword.jsx';
import ResetPassword from './public/recoveryPassword/ResetPassword.jsx';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Rutas públicas protegidas por PublicRoute */}
        <Route element={<PublicRoute />}>
            <Route path="/login" element={<Login />} />
            <Route path="/" element={<Login />} />
            <Route path='/register' element={<Register />} />
            <Route path='*' element={<NotFound />} />
            <Route path='/recuperar-cuenta' element={<RecoveryPassword />} />
            <Route path='/reset-password' element={<ResetPassword />} />
        </Route>

        {/* Rutas privadas protegidas por PrivateRoute */}
        <Route element={<PrivateRoute />}>
            <Route path='/home' element={<Home />} />
            <Route path='/perfil' element={<Perfil />} />
            <Route path='/prestamo' element={<Prestamo />} />
            <Route path='/prestamos-solicitados' element={<PrestamoSolicitado />} /> 
            <Route path='/pagos' element={<Pago />} />
          </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
