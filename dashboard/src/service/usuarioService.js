import ApiAxios from "../utils/axiosInterceptor";

// Obtener los datos del usuario autenticado (actual)
export const getDataUsername = async () => {
    const response = await ApiAxios.get("/admin/usuarios");
    return response.data;
};

// Obtener todos los usuarios
export const getAllUsuarios = async () => {
    try {
        const response = await ApiAxios.get(`/admin/usuarios`);
        return response.data;
    } catch (error) {
        console.error("Error al obtener el usuario>", error);
        throw error;
    }
};

// Actualizar usuario
export const updateUser = async (nuevosDatos) => {
    const response = await ApiAxios.put(`/admin/usuarios/${nuevosDatos.usuarioId}`, {
        nombreCompleto: nuevosDatos.nombreCompleto,
        fechaNacimiento: nuevosDatos.fechaNacimiento,
        direccion: nuevosDatos.direccion
    });

    if (response.status !== 200) throw new Error("Error al actualizar los datos");
    return response.data;
};

// Función para eliminar usuario
export const deleteUser = async (usuarioId) => {
    try {
        const response = await ApiAxios.delete(`/admin/usuarios/${usuarioId}`);
        return response.status;
    } catch (error) {
        throw ("Error al eliminar el usuario ", error);
    }
};
