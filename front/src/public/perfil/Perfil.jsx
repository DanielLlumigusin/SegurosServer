import React, { useState } from "react";
import ImgDefault from "../../assets/img/perfil-default.png";
import useUsuario from "../../hooks/useUsuario";
import "./Perfil.css";

const Perfil = () => {
  const {usuario, error, actualizarUsuario } = useUsuario();
  const [editMode, setEditMode] = useState(false);
  const [formData, setFormData] = useState({
    nombreCompleto: "",
    cedula: "",
    fechaNacimiento: "",
    direccion: "",
  });

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
    actualizarUsuario({ ...formData});
    setEditMode(false);
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
            <input
              type="text"
              name="nombreCompleto"
              value={formData.nombreCompleto}
              onChange={handleChange}
              className="perfil__input"
            />
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
            <input
              type="date"
              name="fechaNacimiento"
              value={formData.fechaNacimiento}
              onChange={handleChange}
              className="perfil__input"
            />
          ) : (
            formatFechaNacimiento(usuario.fechaNacimiento)
          )}
        </p>
        <p>
          <strong>Dirección: </strong>
          {editMode ? (
            <input
              type="text"
              name="direccion"
              value={formData.direccion}
              onChange={handleChange}
              className="perfil__input"
            />
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
