import "./Header.css"


const Header = () => {
    
    function logOut(){
        localStorage.removeItem("token");
        window.location.href="/login";
    }

    return (
        <div>
            <title>SEGUROS</title>
            <header>
                <h1 className="title-dashboard">Seguros</h1>
                <div>
                    <h2 className="username-dashboard">Daniel Gonzalo</h2>
                    <button className="btn-logout" onClick={(e) => logOut()}>Salir</button>
                </div>
            </header>
        </div>
    )
}

export default Header;