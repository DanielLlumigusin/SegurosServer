
import {useState} from 'react';
import { sendCredential } from './LoginDashboard';
import axios from 'axios';
import { URLBASE } from '../../utils/tools';

const LoginDashboard = () => {
    const [username, setUsername] = useState();
    const [password, setPassword] = useState();

    const verifyCount = async() => {
        await axios.get(`${URLBASE}/usuarios/verify`,)
    }
    


    return (
        <>

            <input type="email" placeholder="Username" onChange={e => setUsername(e.target.value)}></input>
            <input type="password" placeholder="Contraseña" onChange={e => setPassword(e.target.value)}></input>
            <button onClick={() => sendCredential(username, password)}>Enviar</button>
        </>
    );
}

export default LoginDashboard;