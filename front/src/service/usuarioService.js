import ApiAxios from "../utils/axiosInterceptor";

// Obtiene los datos por username
export const getDataUser = async () => {
    try {
        const response = await ApiAxios.get(`/api/usuarios/data-user`);
        return response.data;
    } catch (error) {
        console.error("Error al obtener el usuario con username ", error);
        throw error;
    }
};

// Actualiza los datos del usuario
export const updateUser = async (nuevosDatos) => {
    try {
         await ApiAxios.put(`/api/usuarios`, {
                nombreCompleto:nuevosDatos.nombreCompleto,
                fechaNacimiento: nuevosDatos.fechaNacimiento,
                direccion:nuevosDatos.direccion
        });
    } catch (error) {
        console.error("Error al actualizar al usuario", error);
        throw error
    }
};

//Crea un nuevo Usuario
export const createUser = async (usuario) => {
    try {
        await ApiAxios.post('/api/usuarios',{usuario});
    } catch (error) {
        console.error("Error al crear un usuario", error);
        throw error;
    }
}