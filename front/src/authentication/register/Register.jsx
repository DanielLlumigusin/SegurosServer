import { useState } from "react";
import useRegister from "../../hooks/useRegister";
import "./Register.css";

const Register = () => {
    const [formData, setFormData] = useState({
        nombreCompleto: "",
        identificacion: "",
        fechaNacimiento: "",
        direccion: "",
        correoElectronico: "",
        password: ""
    });

    const [errors, setErrors] = useState({});
    const { registrar, loading, mensaje } = useRegister();

    const validate = () => {
        let newErrors = {};

        // Validar Nombre Completo (solo letras, sin caracteres especiales)
        if (!formData.nombreCompleto.trim()) {
            newErrors.nombreCompleto = "El nombre no puede estar vacío";
        } else if (!/^[A-Za-zÁÉÍÓÚáéíóúñÑ\s]+$/.test(formData.nombreCompleto)) {
            newErrors.nombreCompleto = "El nombre solo puede contener letras y espacios";
        }

        // Validar Identificación (solo números, exactamente 10 dígitos)
        if (!formData.identificacion.trim()) {
            newErrors.identificacion = "La identificación no puede estar vacía";
        } else if (!/^\d{10}$/.test(formData.identificacion)) {
            newErrors.identificacion = "La identificación debe tener exactamente 10 dígitos";
        }

        // Validar Fecha de Nacimiento (entre 18 y 90 años)
        if (!formData.fechaNacimiento) {
            newErrors.fechaNacimiento = "Debe ingresar una fecha de nacimiento";
        } else {
            const birthDate = new Date(formData.fechaNacimiento);
            const today = new Date();
            const age = today.getFullYear() - birthDate.getFullYear();
            if (age < 18 || age > 90) {
                newErrors.fechaNacimiento = "Debe tener entre 18 y 90 años";
            }
        }

        // Validar Correo Electrónico
        if (!formData.correoElectronico.trim()) {
            newErrors.correoElectronico = "El correo no puede estar vacío";
        } else if (!/^[\w.-]+@[a-zA-Z\d.-]+\.[a-zA-Z]{2,}$/.test(formData.correoElectronico)) {
            newErrors.correoElectronico = "Ingrese un correo electrónico válido";
        }

        // Validar Dirección (no vacía)
        if (!formData.direccion.trim()) {
            newErrors.direccion = "La dirección no puede estar vacía";
        }

        // Validar Contraseña (no vacía, máximo 20 caracteres)
        if (!formData.password.trim()) {
            newErrors.password = "La contraseña no puede estar vacía";
        } else if (formData.password.length > 20) {
            newErrors.password = "La contraseña no puede tener más de 20 caracteres";
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = () => {
        if (validate()) {
            registrar(formData);
        }
    };

    return (
        <div className="register-background">
            <div className="form-container">
                <h2>Registro</h2>

                <input
                    type="text"
                    name="nombreCompleto"
                    placeholder="Nombre Completo"
                    value={formData.nombreCompleto}
                    onChange={handleChange}
                />
                {errors.nombreCompleto && <p className="error">{errors.nombreCompleto}</p>}

                <input
                    type="text"
                    name="identificacion"
                    placeholder="Cédula"
                    value={formData.identificacion}
                    onChange={handleChange}
                    maxLength="10"
                />
                {errors.identificacion && <p className="error">{errors.identificacion}</p>}

                <input
                    type="date"
                    name="fechaNacimiento"
                    value={formData.fechaNacimiento}
                    onChange={handleChange}
                />
                {errors.fechaNacimiento && <p className="error">{errors.fechaNacimiento}</p>}

                <input
                    type="text"
                    name="direccion"
                    placeholder="Dirección"
                    value={formData.direccion}
                    onChange={handleChange}
                />
                {errors.direccion && <p className="error">{errors.direccion}</p>}

                <input
                    type="email"
                    name="correoElectronico"
                    placeholder="Correo Electrónico"
                    value={formData.correoElectronico}
                    onChange={handleChange}
                />
                {errors.correoElectronico && <p className="error">{errors.correoElectronico}</p>}

                <input
                    type="password"
                    name="password"
                    placeholder="Contraseña"
                    value={formData.password}
                    onChange={handleChange}
                    maxLength="20"
                />
                {errors.password && <p className="error">{errors.password}</p>}

                <button onClick={handleSubmit} disabled={loading}>
                    {loading ? "Registrando..." : "Registrarse"}
                </button>
                <span>¿Tienes cuenta? <a href="/" className="login-link">Iniciar Sesión</a></span>
                {mensaje && <p>{mensaje}</p>}
            </div>
        </div>
    );
};

export default Register;