import { useState } from "react";
import axios from "axios";
import { URLBASE } from '../../utils/tools';
import { useNavigate } from "react-router-dom";
import './Register.css';

const Register = () => {
    const [nombreCompleto, setNombreCompleto] = useState("");
    const [identificacion, setIdentificacion] = useState("");
    const [fechaNacimiento, setFechaNacimiento] = useState("");
    const [direccion, setDireccion] = useState("");
    const [correoElectronico, setCorreoElectronico] = useState("");
    const [password, setPassword] = useState("");
    const [mensaje, setMensaje] = useState("");

    const navigate = useNavigate();

    const validarNombre = (value) => /^[a-zA-Z\s]*$/.test(value);
    const validarCedula = (value) => /^[0-9]*$/.test(value);

    async function registrar() {
        if (!nombreCompleto || !identificacion || !fechaNacimiento || !direccion || !correoElectronico || !password) {
            setMensaje("Todos los campos son obligatorios");
            return;
        }

        try {
            await axios.post(`${URLBASE}/auth/register`, {
                nombreCompleto,
                cedula: identificacion,
                fechaNacimiento,
                direccion,
                username: correoElectronico,
                password
            });

            setMensaje("Registro exitoso");
            navigate('/login');
        } catch (error) {
            setMensaje("Error en el registro: " + (error.response?.data?.message || error.message));
        }
    }

    const returnToLogin = () => {
        navigate('/login');
    };

    return (
        <div className="form-container">
            <h2>Registro</h2>
            <input type="text" placeholder="Nombre Completo" value={nombreCompleto}
                   onChange={e => validarNombre(e.target.value) ? setNombreCompleto(e.target.value) : null}/>
            <input type="text" placeholder="Cédula" value={identificacion}
                   onChange={e => validarCedula(e.target.value) ? setIdentificacion(e.target.value) : null}/>
            <input type="date" value={fechaNacimiento} onChange={e => setFechaNacimiento(e.target.value)}/>
            <input type="text" placeholder="Dirección" value={direccion} onChange={e => setDireccion(e.target.value)}/>
            <input type="email" placeholder="Correo Electrónico" value={correoElectronico}
                   onChange={e => setCorreoElectronico(e.target.value)}/>
            <input type="password" placeholder="Contraseña" value={password} onChange={e => setPassword(e.target.value)}
                   maxLength="20"/>

            <button onClick={registrar}>Registrarse</button>
            <button onClick={returnToLogin}>Cancelar</button>

            {mensaje && <p>{mensaje}</p>}
        </div>
    );
}

export default Register;
