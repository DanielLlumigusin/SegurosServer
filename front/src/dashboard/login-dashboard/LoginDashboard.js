
function validateDataString(text){

    return text;
}

export function sendCredential(username, password){
    username = validateDataString(username);
    password = validateDataString(password);

    if(username=='daniel@info.com' && password=='1234'){
        return window.location.href = '/a';
    }else{
        alert("Credenciales incorrectas");
    }
}


