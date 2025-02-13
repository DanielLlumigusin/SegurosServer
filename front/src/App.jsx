
import './App.css'
import {BrowserRouter, Routes, Route} from "react-router-dom";
import LoginDashboard from './dashboard/login-dashboard/LoginDasboard';
import Dashboard from './dashboard/index/dashboard.jsx';

function App() {
 

  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<LoginDashboard />}></Route>
          <Route path="/dashboard" element={<Dashboard />}></Route>
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
