
import {useState} from 'react';
import { sendCredential } from './LoginDashboard';

const LoginDashboard = () => {
    const [username, setUsername] = useState();
    const [password, setPassword] = useState();

    return (
        <>

            <input type="email" placeholder="Username" onChange={e => setUsername(e.target.value)}></input>
            <input type="password" placeholder="Contraseña" onChange={e => setPassword(e.target.value)}></input>
            <button onClick={() => sendCredential(username, password)}>Enviar</button>
        </>
    );
}

export default LoginDashboard;