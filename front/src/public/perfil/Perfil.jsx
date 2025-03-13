import React, { useState } from "react";
import ImgDefault from "../../assets/img/perfil-default.png";
import useUsuario from "../../hooks/useUsuario";
import "./Perfil.css";

const Perfil = () => {
  const { usuario, error, actualizarUsuario } = useUsuario();
  const [editMode, setEditMode] = useState(false);
  const [formData, setFormData] = useState({
    nombreCompleto: "",
    fechaNacimiento: "",
    direccion: "",
  });

  const [errors, setErrors] = useState({});

  if (error) {
    return <div className="perfil__error">{error}</div>;
  }

  if (!usuario) {
    return <div className="perfil__loading">Cargando...</div>;
  }

  const formatFechaNacimiento = (fecha) => {
    if (!fecha) return "Fecha no disponible";
    const date = new Date(fecha);
    return date.toLocaleDateString("es-ES");
  };

  const validateForm = () => {
    const newErrors = {};

    // Validar Nombre Completo
    if (!formData.nombreCompleto.trim()) {
      newErrors.nombreCompleto = "El nombre no puede estar vacío.";
    } else if (!/^[a-zA-ZÀ-ÿ\s]+$/.test(formData.nombreCompleto)) {
      newErrors.nombreCompleto = "El nombre solo puede contener letras y espacios.";
    }

    // Validar Fecha de Nacimiento (entre 18 y 60 años)
    if (!formData.fechaNacimiento) {
      newErrors.fechaNacimiento = "La fecha de nacimiento es obligatoria.";
    } else {
      const fechaNacimiento = new Date(formData.fechaNacimiento);
      const hoy = new Date();
      let edad = hoy.getFullYear() - fechaNacimiento.getFullYear();
      const mesDiff = hoy.getMonth() - fechaNacimiento.getMonth();
      const diaDiff = hoy.getDate() - fechaNacimiento.getDate();

      if (mesDiff < 0 || (mesDiff === 0 && diaDiff < 0)) {
        edad--; // Ajustar la edad si el cumpleaños aún no ha pasado este año
      }

      if (edad < 18) {
        newErrors.fechaNacimiento = "Debes tener al menos 18 años.";
      } else if (edad > 80) {
        newErrors.fechaNacimiento = "No puedes ser mayor de 80 años.";
      }
    }

    // Validar Dirección (Solo letras, números y espacios)
    if (!formData.direccion.trim()) {
      newErrors.direccion = "La dirección no puede estar vacía.";
    } else if (!/^[a-zA-Z0-9À-ÿ\s]+$/.test(formData.direccion)) {
      newErrors.direccion = "La dirección solo puede contener letras, números y espacios.";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleEditClick = () => {
    setFormData({
      usuarioId: usuario.usuarioId,
      nombreCompleto: usuario.nombreCompleto,
      fechaNacimiento: usuario.fechaNacimiento.slice(0, 10),
      direccion: usuario.direccion,
    });
    setEditMode(true);
  };

  const handleSaveClick = () => {
    if (validateForm()) {
      actualizarUsuario({ ...formData });
      setEditMode(false);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  return (
    <div className="perfil">
      <div className="perfil__header">
        <img className="perfil__imagen" src={ImgDefault} alt="Perfil" />
        <h3 className="perfil__nombre">
          {editMode ? (
            <>
              <input
                type="text"
                name="nombreCompleto"
                value={formData.nombreCompleto}
                onChange={handleChange}
                className="perfil__input"
              />
              {errors.nombreCompleto && <p className="perfil__error">{errors.nombreCompleto}</p>}
            </>
          ) : (
            usuario.nombreCompleto
          )}
        </h3>
      </div>

      <div className="perfil__info">
        <p>
          <strong>Cédula: </strong>
          {usuario.cedula}
        </p>
        <p>
          <strong>Fecha de Nacimiento: </strong>
          {editMode ? (
            <>
              <input
                type="date"
                name="fechaNacimiento"
                value={formData.fechaNacimiento}
                onChange={handleChange}
                className="perfil__input"
              />
              {errors.fechaNacimiento && <p className="perfil__error">{errors.fechaNacimiento}</p>}
            </>
          ) : (
            formatFechaNacimiento(usuario.fechaNacimiento)
          )}
        </p>
        <p>
          <strong>Dirección: </strong>
          {editMode ? (
            <>
              <input
                type="text"
                name="direccion"
                value={formData.direccion}
                onChange={handleChange}
                className="perfil__input"
              />
              {errors.direccion && <p className="perfil__error">{errors.direccion}</p>}
            </>
          ) : (
            usuario.direccion
          )}
        </p>
        <p>
          <strong>Username: </strong>
          <span>{usuario.username}</span>
        </p>
      </div>

      <div className="perfil__acciones">
        {editMode ? (
          <button className="perfil__boton perfil__boton--guardar" onClick={handleSaveClick}>
            Guardar
          </button>
        ) : (
          <button className="perfil__boton perfil__boton--editar" onClick={handleEditClick}>
            Editar
          </button>
        )}
      </div>
    </div>
  );
};

export default Perfil;
