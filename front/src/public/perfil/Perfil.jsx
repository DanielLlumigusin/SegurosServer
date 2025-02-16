import React, { useState, useEffect } from "react";
import { getDataUsername } from "../../utils/tools";
import { jwtDecode } from "jwt-decode";

const Perfil = () => {
    const [usuario, setUsuario] = useState(null);
    const [username, setUsername] = useState(null);
    const [error, setError] = useState(null);
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

    if (error) {
        return <div>{error}</div>;
    }

    if (!usuario || !usuario.roles) {
        return <div>Cargando...</div>;
    }

    const formatFechaNacimiento = (fecha) => {
        if (!fecha) return "Fecha no disponible";
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