import { useState, useEffect } from "react";
import { getDataUsername, updateUser, getAllUsuarios, deleteUser } from "../service/usuarioService";

const useUsuario = () => {
    const [usuario, setUsuario] = useState(null);  
    const [listaUsuarios, setListaUsuarios] = useState([]);
    const [error, setError] = useState(null);
    const [loadingUsuario, setLoadingUsuario] = useState(true);
    const [loadingUsuarios, setLoadingUsuarios] = useState(true);

    // Cargar datos del usuario autenticado
    const fetchUsuario = async () => {
        try {
            setLoadingUsuario(true);
            const data = await getDataUsername();
            setUsuario(data);
        } catch (error) {
            console.error("Error al obtener usuario:", error);
            setError("Error al obtener datos del usuario");
        } finally {
            setLoadingUsuario(false);
        }
    };

    // Cargar todos los usuarios
    const fetchUsuarios = async () => {
        try {
            setLoadingUsuarios(true);
            const data = await getAllUsuarios();
            setListaUsuarios(data);
        } catch (error) {
            console.error("Error al obtener la lista de usuarios:", error);
            setError("Error al obtener la lista de usuarios");
        } finally {
            setLoadingUsuarios(false);
        }
    };

    // Se usa useEffect para cargar los datos al iniciar
    useEffect(() => {
        fetchUsuario();
        fetchUsuarios();
    }, []); 

    const actualizarUsuario = async (nuevosDatos) => {
        try {
            const updatedUser = await updateUser(nuevosDatos);
            setUsuario(updatedUser);
            return updatedUser;
        } catch (error) {
            console.error("Error al actualizar usuario:", error);
            setError("Error al actualizar los datos");
        }
    };

    const eliminarUsuario = async (usuarioId) => {
        try {
            await deleteUser(usuarioId);
        } catch (error) {
            console.log("Error al eliminar usuario", error);
            setError("Error al eliminar al Usuario");
        }
    }

    return { 
        usuario, 
        listaUsuarios, 
        error, 
        actualizarUsuario, 
        eliminarUsuario,
        loadingUsuario, 
        loadingUsuarios, 
        fetchUsuarios 
    };
};

export default useUsuario;
