import React from "react";
import PrestamoCard from "./PrestamoCard";

const ListaPrestamos = ({ prestamos, titulo }) => {
    if (!prestamos || prestamos.length === 0) {
        return <p className="prestamo-sin-datos">No hay {titulo.toLowerCase()}.</p>;
    }

    return (
        <div className="prestamo-lista-container">
            <h2 className="prestamo-lista-title">{titulo}</h2>
            <div className="prestamo-grid">
                {prestamos.map((prestamo) => (
                    <PrestamoCard key={prestamo.prestamoId} prestamo={prestamo} />
                ))}
            </div>
        </div>
    );
};

export default ListaPrestamos;
