import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const useRegister = () => {
    const [loading, setLoading] = useState(false);
    const [mensaje, setMensaje] = useState("");
    const navigate = useNavigate();

    const validarCampos = (nombreCompleto, identificacion, fechaNacimiento, direccion, correoElectronico, password) => {
        if (!nombreCompleto || !identificacion || !fechaNacimiento || !direccion || !correoElectronico || !password) {
            return "Todos los campos son obligatorios.";
        }
        if (!/^[a-zA-Z\s]*$/.test(nombreCompleto)) {
            return "El nombre solo puede contener letras y espacios.";
        }
        if (!/^\d+$/.test(identificacion)) {
            return "La cédula solo puede contener números.";
        }
        return "";
    };

    const registrar = async (datos) => {
        const error = validarCampos(datos.nombreCompleto, datos.identificacion, datos.fechaNacimiento, datos.direccion, datos.correoElectronico, datos.password);
        if (error) {
            setMensaje(error);
            return;
        }

        setLoading(true);
        setMensaje("");

        try {
            await axios.post(`${URLBASE}/auth/register`, {
                nombreCompleto: datos.nombreCompleto,
                cedula: datos.identificacion,
                fechaNacimiento: datos.fechaNacimiento,
                direccion: datos.direccion,
                username: datos.correoElectronico,
                password: datos.password
            });

            setMensaje("Registro exitoso");
            navigate('/login');
        } catch (error) {
            setMensaje("Error en el registro: " + (error.response?.data?.message || error.message));
        } finally {
            setLoading(false);
        }
    };

    return { registrar, loading, mensaje, setMensaje };
};

export default useRegister;
