import ApiAxios from "../utils/axiosInterceptor";
import { URLBASE } from "../utils/tools";

export const getDataUsername = async (username) => {
    const response = await ApiAxios.get(`${URLBASE}/api/usuarios/username`,
        {
            params: {
                username
            }
        });
    return response.data;
};

export const updateUser = async (nuevosDatos) => {
    // Llama a tu API para actualizar los datos del usuario
    const response = await ApiAxios.put(`${URLBASE}/api/usuarios/${nuevosDatos.usuarioId}`, {
            nombreCompleto:nuevosDatos.nombreCompleto,
            fechaNacimiento: nuevosDatos.fechaNacimiento,
            direccion:nuevosDatos.direccion
    });
    if (!response.ok) throw new Error("Error al actualizar los datos");
    return await response.json();
};
