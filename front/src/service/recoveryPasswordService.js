import ApiAxios from "../utils/axiosInterceptor";

export const sendMailRecoveryPassword = async (email) => {
    try {
        const response = await ApiAxios.post("/auth/send-email", { username: email });
        return response.data;
    } catch (error) {
        console.error("Hubo un problema al enviar el mensaje:", error);
        throw new Error(error.response?.data || "No se pudo enviar el correo.");
    }
};


export const verifyResetPassword = async (token) => {
    try {
        const response = await ApiAxios.get("/auth/reset-password", {
            params: { token }
        });
        return response.data;
    } catch (error) {
        console.error("No se pudo validar el intento de restablecer la contraseña", error);
        throw error;
    }
};

export const resetPassword = async (token, newPassword, confirmPassword) => {
    try {
        const requestBody = {
            token: token,
            newPassword: newPassword,
            confirmPassword: confirmPassword
        };
        const response = await ApiAxios.post("/auth/reset-password", requestBody);
        
        return response.data
    } catch (error) {
        console.error(error);
        throw error;
    }
}

