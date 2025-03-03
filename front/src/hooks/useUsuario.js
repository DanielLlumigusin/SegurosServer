import { useState, useEffect } from "react";
import { getDataUser, updateUser } from "../service/usuarioService";
import { useNavigate } from "react-router-dom";

const useUsuario = () => {
  const [usuario, setUsuario] = useState(null);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const datosUsuario = async () => {
    try {
      const response = await getDataUser();
      setUsuario(response);
    } catch (error) {
      console.error("Error obteniendo datos del usuario:", error);
      setError("No se pudo obtener los datos del usuario.");
    }
  };

  const actualizarUsuario = async (updateUsuario) => {
    try {
      const updatedUser = await updateUser(updateUsuario);
      await datosUsuario();
    } catch (error) {
      console.error("Error actualizando usuario:", error);
      setError("Error al actualizar los datos.");
    }
  };

  useEffect(() => {
    datosUsuario();
  }, []);

  return { usuario, error, datosUsuario, actualizarUsuario };
};

export default useUsuario;
