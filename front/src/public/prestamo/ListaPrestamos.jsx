import React from "react";
import PrestamoCard from "./PrestamoCard";

const ListaPrestamos = ({ prestamos, titulo }) => {
    if (!prestamos || prestamos.length === 0) {
        return <p>No hay {titulo.toLowerCase()}.</p>;
    }

    return (
        <div>
            <h2>{titulo}</h2>
            {prestamos.map((prestamo) => (
                <PrestamoCard key={prestamo.prestamoId} prestamo={prestamo} />
            ))}
        </div>
    );
};

export default ListaPrestamos;