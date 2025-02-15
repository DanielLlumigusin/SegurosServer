import { useState, useEffect } from "react";
import ApiAxios from "../../utils/axiosInterceptor";
import { URLBASE } from "../../utils/tools";
import Header from "../../dashboard/components/header/Header";
const tasasInteres = {
    "Básica": 5.0,
    "Premium": 8.5,
    "VIP": 12.0
};

// Función para calcular la cuota del préstamo según el método francés
const calcularCuota = (monto, plazo, tasaInteres, tipoPago) => {
    if (!monto || !plazo || !tasaInteres) return 0;

    let tasaDecimal = tasaInteres / 100;
    let plazoAjustado = plazo;

    if (tipoPago === "MENSUAL") {
        tasaDecimal /= 12;
    } else if (tipoPago === "SEMANAL") {
        tasaDecimal /= 52;
        plazoAjustado *= 4; // Convertir meses a semanas
    } else if (tipoPago === "ANUAL") {
        plazoAjustado = Math.ceil(plazo / 12); // Convertir meses a años
    }

    return (monto * tasaDecimal) / (1 - Math.pow(1 + tasaDecimal, -plazoAjustado));
};

// Función para generar la tabla de amortización
const generarTablaAmortizacion = (monto, plazo, tasaInteres, tipoPago) => {
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
        tabla.push({ periodo: i, cuota: cuota.toFixed(2), interes: interes.toFixed(2), capital: capital.toFixed(2), saldoPendiente: saldoPendiente.toFixed(2) });
    }

    return tabla;
};

const Prestamo = () => {
    const [monto, setMonto] = useState(1000);
    const [plazo, setPlazo] = useState(12);
    const [tipoTasa, setTipoTasa] = useState("Básica");
    const [tipoPago, setTipoPago] = useState("MENSUAL");
    const [mensaje, setMensaje] = useState("");
    const [cuota, setCuota] = useState(0);
    const [tablaAmortizacion, setTablaAmortizacion] = useState([]);

    const tasaInteres = tasasInteres[tipoTasa];

    useEffect(() => {
        setCuota(calcularCuota(monto, plazo, tasaInteres, tipoPago).toFixed(2));
        setTablaAmortizacion(generarTablaAmortizacion(monto, plazo, tasaInteres, tipoPago));
    }, [monto, plazo, tasaInteres, tipoPago]);

    const solicitarPrestamo = async (e) => {
        e.preventDefault();
    
        // Validaciones
        if (isNaN(monto) || monto < 500 || monto > 50000) {
            setMensaje("El monto debe estar entre $500 y $50,000.");
            return;
        }
        if (isNaN(plazo) || plazo < 6 || plazo > 60) {
            setMensaje("El plazo debe estar entre 6 y 60 meses.");
            return;
        }
    
        try {
            const token = localStorage.getItem("token");
            if (!token) {
                setMensaje("No se encontró el token de autenticación. Por favor, inicie sesión.");
                return;
            }
    
            await ApiAxios.post(
                `${URLBASE}/api/prestamos`,
                {
                    monto_solicitado: parseFloat(monto),
                    plazo_amortizacion: parseInt(plazo),
                    tasa_interes: tasaInteres,
                    tipo_pago: tipoPago,
                },
                {
                    headers: { Authorization: `Bearer ${token}` }
                }
            );
    
            setMensaje("Préstamo solicitado con éxito.");
            // Reiniciar el formulario después de una solicitud exitosa
            setMonto(0);
            setPlazo(6);
            setTasaInteres(0);
            setTipoPago("");
        } catch (error) {
            if (error.response) {
                // Error del servidor (4xx o 5xx)
                setMensaje("Error al solicitar préstamo: " + (error.response.data?.message || error.message));
            } else if (error.request) {
                // Error de red (no se recibió respuesta)
                setMensaje("Error de red. Por favor, verifica tu conexión.");
            } else {
                // Error inesperado
                setMensaje("Error inesperado: " + error.message);
            }
        }
    };

    return (
        <div>
            <Header />
            <h1>Solicitar Préstamo</h1>
            <p>Nota: Solo se puede hacer un solo préstamo por persona</p>

            {mensaje && <p style={{ color: "red" }}>{mensaje}</p>}

            <form onSubmit={solicitarPrestamo}>
                <div>
                    <label>Monto Solicitado:</label>
                    <input type="number" value={monto} onChange={(e) => setMonto(e.target.value)} required />
                </div>

                <div>
                    <label>Plazo de Amortización ({tipoPago}):</label>
                    <input type="number" value={plazo} onChange={(e) => setPlazo(e.target.value)} required />
                </div>

                <div>
                    <label>Tasa de Interés:</label>
                    <select value={tipoTasa} onChange={(e) => setTipoTasa(e.target.value)}>
                        {Object.keys(tasasInteres).map((tipo) => (
                            <option key={tipo} value={tipo}>{tipo} ({tasasInteres[tipo]}%)</option>
                        ))}
                    </select>
                </div>

                <div>
                    <label>Tipo de Pago:</label>
                    <select value={tipoPago} onChange={(e) => setTipoPago(e.target.value)}>
                        <option value="MENSUAL">Mensual</option>
                        <option value="SEMANAL">Semanal</option>
                        <option value="ANUAL">Anual</option>
                    </select>
                </div>

                <h3>Tabla de Amortización</h3>
                <table border="1">
                    <thead>
                        <tr>
                            <th>Periodo</th>
                            <th>Cuota</th>
                            <th>Interés</th>
                            <th>Capital</th>
                            <th>Saldo Pendiente</th>
                        </tr>
                    </thead>
                    <tbody>
                        {tablaAmortizacion.map((fila, index) => (
                            <tr key={index}>
                                <td>{fila.periodo}</td>
                                <td>${fila.cuota}</td>
                                <td>${fila.interes}</td>
                                <td>${fila.capital}</td>
                                <td>${fila.saldoPendiente}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>

                <button type="submit">Solicitar Préstamo</button>
            </form>
        </div>
    );
};

export default Prestamo;