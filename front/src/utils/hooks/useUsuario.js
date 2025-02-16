import { useState, useEffect } from "react";
import { jwtDecode } from "jwt-decode";
import { getDataUsername } from "../../service/usuarioService";

const useUsuario = () => {
    const [usuario, setUsuario] = useState(null);
    const [username, setUsername] = useState();
    const [error, setError] = useState();
    const token = localStorage.getItem("token");

    useEffect(() => {
        if (token) {
            try {
                const payload = jwtDecode(token);
                const currentTime = Date.now() / 1000;

                if (payload.exp < currentTime) {
                    console.error("El token ha expirado.");
                    localStorage.removeItem("token");
                    setError("El token ha expirado. Por favor, inicia sesión nuevamente.");
                    window.location.href = "/login";
                    return;
                }

                const decodedUsername = payload.sub;
                setUsername(decodedUsername);
            } catch (error) {
                console.error("Error al decodificar el token:", error);
                setError("Error al decodificar el token");
                window.location.href = "/login";
            }
        } else {
            setError("No se encontró el token en localStorage");
            window.location.href = "/login";
        }
    }, [token]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await getDataUsername(username);
                setUsuario(data);
            } catch (error) {
                setError(error.message);
            }
        };

        if (username) {
            fetchData();
        }
    }, [username]);

    return { usuario, error };
};

export default useUsuario;