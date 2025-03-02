import { useState } from 'react'
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css'
import Login from './auth/Login';
import Home from './screens/home/Home';
import GestionUsuarios from './screens/usuarios/GestionUsuarios';
import Sidebar from './screens/components/sidebar/Sidebar';
import ProtectedRoute from './ProtectedRoute';

function App() {
  return (
    <>
      <BrowserRouter>
      <Sidebar />
      <Routes>
        <Route path="/login" element={<Login />} />
        
        {/* Agrupamos las rutas protegidas dentro de ProtectedRoute */}
        <Route element={<ProtectedRoute />}>
          <Route path="/home" element={<Home />} />
          <Route path="/usuarios" element={<GestionUsuarios />} />
        </Route>

        {/* Si no hay coincidencias, redirigir al login */}
        <Route path="*" element={<Login />} />
      </Routes>
    </BrowserRouter>
    </>
  )
}

export default App
