import React, { useState, useEffect } from "react";
import ApiAxios from "../../utils/axiosInterceptor";
import { URLBASE } from "../../utils/tools";
import { jwtDecode } from "jwt-decode";

const Perfil = () => {
    const [usuario, setUsuario] = useState(null);
    const [username, setUsername] = useState(null);
    const [error, setError] = useState(null);

    const token = localStorage.getItem("token");
    console.log("Token en localStorage:", token);

    useEffect(() => {
        if (token) {
            try {
                const payload = jwtDecode(token);
                const currentTime = Date.now() / 1000;

                if (payload.exp < currentTime) {
                    console.error("El token ha expirado.");
                    localStorage.removeItem("token");
                    setError("El token ha expirado. Por favor, inicia sesión nuevamente.");
                    return;
                }

                const decodedUsername = payload.sub;
                setUsername(decodedUsername);
            } catch (error) {
                console.error("Error al decodificar el token:", error);
                setError("Error al decodificar el token");
            }
        } else {
            setError("No se encontró el token en localStorage");
        }
    }, [token]);

    const getDataUsername = async () => {
        try {
            console.log("Username codificado:", username);

            const response = await ApiAxios.get(`${URLBASE}/api/usuarios/username`, {
                params: {
                    username: username,
                }
            });
            console.log("Respuesta del servidor:", response.data);
            setUsuario(response.data);
        } catch (error) {
            console.error("Error en la solicitud:", error);
            setError("Error al obtener los datos del usuario");
        }
    };

    useEffect(() => {
        if (username && token) {
            getDataUsername();
        }
    }, [username, token]);

    if (error) {
        return <div>{error}</div>;
    }

    if (!usuario) {
        return <div>Cargando...</div>;
    }

    const formatFechaNacimiento = (fecha) => {
        const date = new Date(fecha);
        return date.toLocaleDateString("es-ES");
    };

    return (
        <div>
            <h3>Nombre Completo: {usuario.nombreCompleto}</h3>
            <p>Cédula: {usuario.cedula}</p>
            <p>Fecha de Nacimiento: {formatFechaNacimiento(usuario.fechaNacimiento)}</p>
            <p>Dirección: {usuario.direccion}</p>
            <p>Username: {usuario.username}</p>
            <h4>Roles y Permisos:</h4>
            <ul>
                {usuario.roles.map((rol) => (
                    <li key={rol.id}>
                        <strong>{rol.roleEnum}</strong>
                        <ul>
                            {rol.permisosList.map((permiso) => (
                                <li key={permiso.id}>{permiso.nombre}</li>
                            ))}
                        </ul>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Perfil;