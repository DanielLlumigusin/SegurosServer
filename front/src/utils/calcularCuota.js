// Función para calcular la cuota del préstamo según el método francés
export const calcularCuota = (monto, plazo, tasaInteres, tipoPago) => {
    if (!monto || !plazo || !tasaInteres) return 0;

    let tasaDecimal = tasaInteres / 100;
    let plazoAjustado = plazo;

    if (tipoPago === "MENSUAL") {
        tasaDecimal /= 12;
    } else if (tipoPago === "SEMANAL") {
        tasaDecimal /= 52;
        plazoAjustado *= 4;
    } else if (tipoPago === "ANUAL") {
        plazoAjustado = Math.ceil(plazo / 12);
    }

    return (monto * tasaDecimal) / (1 - Math.pow(1 + tasaDecimal, -plazoAjustado));
};

// Función para generar la tabla de amortización
export const generarTablaAmortizacion = (monto, plazo, tasaInteres, tipoPago) => {
    let saldoPendiente = monto;
    let tasaDecimal = tasaInteres / 100;
    let plazoAjustado = plazo;

    if (tipoPago === "MENSUAL") {
        tasaDecimal /= 12;
    } else if (tipoPago === "SEMANAL") {
        tasaDecimal /= 52;
        plazoAjustado *= 4;
    } else if (tipoPago === "ANUAL") {
        plazoAjustado = Math.ceil(plazo / 12);
    }

    const cuota = calcularCuota(monto, plazo, tasaInteres, tipoPago);
    let tabla = [];

    for (let i = 1; i <= plazoAjustado; i++) {
        let interes = saldoPendiente * tasaDecimal;
        let capital = cuota - interes;
        saldoPendiente -= capital;
        tabla.push({
            periodo: i,
            cuota: cuota.toFixed(2),
            interes: interes.toFixed(2),
            capital: capital.toFixed(2),
            saldoPendiente: saldoPendiente.toFixed(2)
        });
    }

    return tabla;
};