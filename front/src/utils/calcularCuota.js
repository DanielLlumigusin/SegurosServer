// Función para calcular la cuota del préstamo según el método francés
export const calcularCuota = (monto, plazo, tasaInteres, tipoPago) => {
    // ✅ Validaciones para evitar cálculos erróneos
    if (!monto || monto <= 0 || isNaN(monto) || monto > 50000) return 0; // Límite máximo de $50,000
    if (!plazo || plazo <= 0 || isNaN(plazo)) return 0;
    if (!tasaInteres || tasaInteres <= 0 || isNaN(tasaInteres)) return 0;

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

    if (tasaDecimal === 0 || plazoAjustado <= 0) return 0;

    return (monto * tasaDecimal) / (1 - Math.pow(1 + tasaDecimal, -plazoAjustado));
};

// Función para generar la tabla de amortización
export const generarTablaAmortizacion = (monto, plazo, tasaInteres, tipoPago) => {
    // ✅ Validaciones para evitar errores en el cálculo
    if (!monto || monto <= 0 || isNaN(monto) || monto > 50000) return []; // Límite máximo de $50,000
    if (!plazo || plazo <= 0 || isNaN(plazo) || plazo > 60) return [];
    if (!tasaInteres || tasaInteres <= 0 || isNaN(tasaInteres)) return [];

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

    if (tasaDecimal === 0) {
        let cuotaSimple = monto / plazoAjustado;
        return Array.from({ length: plazoAjustado }, (_, i) => ({
            periodo: i + 1,
            cuota: cuotaSimple.toFixed(2),
            interes: "0.00",
            capital: cuotaSimple.toFixed(2),
            saldoPendiente: (monto - cuotaSimple * (i + 1)).toFixed(2)
        }));
    }

    const cuota = calcularCuota(monto, plazo, tasaInteres, tipoPago);
    let tabla = [];

    for (let i = 1; i <= plazoAjustado; i++) {
        let interes = saldoPendiente * tasaDecimal;
        let capital = cuota - interes;

        // ✅ Evitar saldo negativo en la última cuota
        if (saldoPendiente - capital < 0) {
            capital = saldoPendiente;
        }

        saldoPendiente -= capital;

        tabla.push({
            periodo: i,
            cuota: cuota.toFixed(2),
            interes: interes.toFixed(2),
            capital: capital.toFixed(2),
            saldoPendiente: saldoPendiente.toFixed(2)
        });

        if (saldoPendiente <= 0) break;
    }

    return tabla;
};