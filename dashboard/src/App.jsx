import { BrowserRouter, Routes, Route } from "react-router-dom";
import './App.css';
import Login from './auth/Login';
import Home from './screens/home/Home';
import GestionUsuarios from './screens/usuarios/GestionUsuarios';
import GestionPrestamos from './screens/prestamos/GestionPrestamos';
import ProtectedRoute from './ProtectedRoute';
import Layout from './Layout';  
import NotFound from './screens/notFound/NotFound';
import LogActividad from "./screens/logActividad/LogActividad";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Rutas no protegidas */}
        <Route path="/login" element={<Login />} />
        <Route path="/" element={<Login />} />

        {/* Rutas protegidas agrupadas */}
        <Route element={<ProtectedRoute />}>
          <Route element={<Layout />}> 
            <Route path="/home" element={<Home />} />
            <Route path="/usuarios" element={<GestionUsuarios />} />
            <Route path='/prestamos' element={<GestionPrestamos />} />
            <Route path='/historial' element={<LogActividad />} />
          </Route>
        </Route>

        {/* Ruta para páginas no encontradas */}
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
