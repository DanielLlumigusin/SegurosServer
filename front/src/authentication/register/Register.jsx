import { useState } from "react";
import useRegister from "../../utils/hooks/useRegister";
import './Register.css';

const Register = () => {
    const [formData, setFormData] = useState({
        nombreCompleto: "",
        identificacion: "",
        fechaNacimiento: "",
        direccion: "",
        correoElectronico: "",
        password: ""
    });

    const { registrar, loading, mensaje } = useRegister();

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    return (
        <div className="register-background">

            <div className="form-container">
                <h2>Registro</h2>
                <input type="text" name="nombreCompleto" placeholder="Nombre Completo" value={formData.nombreCompleto} onChange={handleChange} />
                <input type="text" name="identificacion" placeholder="Cédula" value={formData.identificacion} onChange={handleChange} />
                <input type="date" name="fechaNacimiento" value={formData.fechaNacimiento} onChange={handleChange} />
                <input type="text" name="direccion" placeholder="Dirección" value={formData.direccion} onChange={handleChange} />
                <input type="email" name="correoElectronico" placeholder="Correo Electrónico" value={formData.correoElectronico} onChange={handleChange} />
                <input type="password" name="password" placeholder="Contraseña" value={formData.password} onChange={handleChange} maxLength="20" />

                <button onClick={() => registrar(formData)} disabled={loading}>
                    {loading ? "Registrando..." : "Registrarse"}
                </button>
                <span>¿Tienes cuenta? <a href="/" className="login-link">Iniciar Sesión</a></span>
                {mensaje && <p>{mensaje}</p>}
            </div>
        </div>
    );
};

export default Register;
