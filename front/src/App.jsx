
import './App.css'
import {BrowserRouter, Routes, Route} from "react-router-dom";
import LoginDashboard from './dashboard/login-dashboard/LoginDasboard';
import Dashboard from './dashboard/index/dashboard.jsx';
import Register from './public/register/Register.jsx';

function App() {
 

  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<LoginDashboard />}></Route>
          <Route path='/register' element={<Register />}></Route>
          <Route path="/dashboard" element={<Dashboard />}></Route>
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
