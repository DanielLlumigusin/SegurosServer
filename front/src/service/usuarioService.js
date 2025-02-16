import ApiAxios from "../utils/axiosInterceptor";
import { URLBASE } from "../utils/tools";

export const getDataUsername = async (username) => {
    const response = await ApiAxios.get(`${URLBASE}/api/usuarios/username`,
        {params:{
            username
    }
});
    return response.data;
};

