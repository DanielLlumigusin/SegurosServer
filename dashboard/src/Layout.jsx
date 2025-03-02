import Sidebar from "./screens/components/sidebar/Sidebar"; 
import { Outlet } from "react-router-dom";

const Layout = () => {
  return (
    <div>
      <Sidebar /> 
      <div className="content">
        <Outlet />  
      </div>
    </div>
  );
};

export default Layout;
