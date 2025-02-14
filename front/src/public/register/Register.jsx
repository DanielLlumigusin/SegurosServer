import { useState } from "react";
import axios from "axios";
import { URLBASE } from '../../utils/tools';

const Register = () => {
    const [nombreCompleto, setNombreCompleto] = useState("");
    const [identificacion, setIdentificacion] = useState("");
    const [fechaNacimiento, setFechaNacimiento] = useState("");
    const [direccion, setDireccion] = useState("");
    const [correoElectronico, setCorreoElectronico] = useState("");
    const [contrasenaHash, setContrasenaHash] = useState("");
    const [mensaje, setMensaje] = useState(""); // Para mostrar mensajes de éxito o error

    async function registrar() {
        if (!nombreCompleto || !identificacion || !fechaNacimiento || !direccion || !correoElectronico || !contrasenaHash) {
            setMensaje("Todos los campos son obligatorios");
            return;
        }

        try {
            const response = await axios.post(`${URLBASE}/usuarios`, {
                nombreCompleto,
                identificacion,
                fechaNacimiento,
                direccion,
                correoElectronico,
                contrasenaHash
            });

            setMensaje("Registro exitoso");
        } catch (error) {
            setMensaje("Error en el registro: " + (error.response?.data?.message || error.message));
        }
    }

    return (
        <div>
            <h2>Registro</h2>
            <input type="text" placeholder="Nombre Completo" value={nombreCompleto} onChange={e => setNombreCompleto(e.target.value)} />
            <input type="text" placeholder="Cédula" value={identificacion} onChange={e => setIdentificacion(e.target.value)} />
            <input type="date" value={fechaNacimiento} onChange={e => setFechaNacimiento(e.target.value)} />
            <input type="text" placeholder="Dirección" value={direccion} onChange={e => setDireccion(e.target.value)} />
            <input type="email" placeholder="Correo Electrónico" value={correoElectronico} onChange={e => setCorreoElectronico(e.target.value)} />
            <input type="password" placeholder="Contraseña" value={contrasenaHash} onChange={e => setContrasenaHash(e.target.value)} />

            <button onClick={registrar}>Registrarse</button>

            {mensaje && <p>{mensaje}</p>}
        </div>
    );
}

export default Register;
