import { useState } from "react";
import { sendMailRecoveryPassword, verifyResetPassword, resetPassword } from "../service/recoveryPasswordService";

const useRecoveryPassword = () => {
    const [message, setMessage] = useState("");
    const [loading, setLoading] = useState(false);

    const sendEmail = async (email) => {
        try {
            setMessage("");
            setLoading(true);
    
            if (!email) {
                setMessage("Por favor ingresa un correo válido.");
                return;
            }
    
            const response = await sendMailRecoveryPassword(email);
            setMessage(response);
        } catch (error) {
            console.error("Error al enviar el correo:", error);
            setMessage(error.message || "No se pudo enviar el correo.");
        } finally {
            setLoading(false);
        }
    };
    

    const verifyTokenResetPassword = async (token) => {
        try {
            setMessage("");
            setLoading(true);
            await verifyResetPassword(token);
        } catch (error) {
            console.error("Error en validar el token: ", error);
            setMessage(error.response?.data?.message || "Tiempo Expirado, vuelve a enviar el correo"); 
        } finally {
            setLoading(false);
        }
    }

    const formResetPassword = async (token, password, confirmPassword) => {
        try {
            setMessage("");
            setLoading(true);
            await resetPassword(token, password, confirmPassword);
            setMessage("Se restablecio su contraseña correctamente. Redirigiendo...")
        } catch (error) {
            console.error("Error en validar el token: ", error);
            setMessage(error.response?.data?.message || "No se pudo verificar el token"); 
        } finally {
            setLoading(false);
        }
    }

    return { message, loading, sendEmail, verifyTokenResetPassword, formResetPassword };
};

export default useRecoveryPassword;
