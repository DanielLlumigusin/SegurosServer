import "./Header.css"


const Header = () => {
    
    function logOut(){
        window.location.href="/";
    }

    return (
        <div>
            <title>DASHBOARD</title>
            <header>
                <h1 className="title-dashboard">Dashboard</h1>
                <div>
                    <h2 className="username-dashboard">Daniel Gonzalo</h2>
                    <button className="btn-logout" onClick={(e) => logOut()}>Salir</button>
                </div>
            </header>
        </div>
    )
}

export default Header;