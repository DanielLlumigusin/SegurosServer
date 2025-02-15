import './App.css';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginDashboard from './dashboard/login-dashboard/LoginDasboard';
import Dashboard from './dashboard/index/dashboard.jsx';
import Register from './public/register/Register.jsx';
import PrivateRoute from './PrivateRoute'; // Importa el PrivateRoute
import PublicRoute from './PublicRoute'; // Importa el PublicRoute

function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Rutas públicas protegidas por PublicRoute */}
        <Route element={<PublicRoute />}>
          <Route path="/login" element={<LoginDashboard />} />
          <Route path='/register' element={<Register />} />
        </Route>

        {/* Rutas privadas protegidas por PrivateRoute */}
        <Route element={<PrivateRoute />}>
          <Route path="/dashboard" element={<Dashboard />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
