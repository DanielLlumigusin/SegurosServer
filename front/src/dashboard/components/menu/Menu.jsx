import "./Menu.css"

const Menu = () => {
    return (
        <>
            <ul>
                <li>
                    <a href="/perfil">Mi perfil</a>
                </li>
                <li>
                    <a href="/prestamo">
                        Solicitar Préstamo
                    </a>
                </li>
                <li>
                    <a href="/prestamos-solicitados">
                        Mis Préstamos
                    </a>
                </li>
                <li>
                    <a href="/pagos">
                        Pagos
                    </a>
                </li>
                <li>
                    <a href="/tabla-amortizacion">Tabla Amortizacion</a>
                </li>
            </ul>
        </>
    )
}

export default Menu;