const Menu = () => {
    return (
        <div>
            <div className="menu-dashboard-container">
                <ul className="menu-list">
                    <li className="menu-item">
                        <a href="gestion-usuario">Gestion de Usuarios</a>
                    </li>
                    <li className="menu-item">
                        <a href="gestion-prestamos">Gestion de Prestamos</a>
                    </li>
                    <li className="menu-item">
                        <a href="gestion-pagos">Gestion de Pagos</a>
                    </li>
                    <li className="menu-item">
                        <a href="gestion-logs">Historial de Acciones</a>
                    </li>
                </ul>
            </div>
        </div>
    );
}


export default Menu;