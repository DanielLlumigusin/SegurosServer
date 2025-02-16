import React from "react";

const Mensaje = ({ mensaje }) => {
    if (!mensaje) return null;

    const color = mensaje.includes("éxito") ? "green" : "red";
    return <p style={{ color }}>{mensaje}</p>;
};

export default Mensaje;