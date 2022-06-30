import { useState } from "react"
import { registerUser } from "../service/apiService";

export default function RegisterPage(){
    const [password, setPassword] = useState("");    
    const [passwordCheck, setPasswordCheck] = useState("");    
    const [username, setUsername] = useState("");
    const [error, setError] = useState("");


    const submitForm = (e) => {
        e.preventDefault();
        if(password != passwordCheck) {
            setError("Passwort stimmt nicht mit Passwort check überein.")
        } else {
            registerUser(username, password).then(res => res.data).then( data => localStorage.setItem("jwt-kanban", data.token))
        }
    }

    return (
        <>
            <form className="LoginForm" onSubmit={submitForm}>
                <input value={username} onChange={(e)=>setUsername(e.target.value)}></input>
                <input value={password} onChange={(e)=>setPassword(e.target.value)}></input>
                <input value={passwordCheck} onChange={(e)=>setPasswordCheck(e.target.value)}></input>
                <input type="submit" value="Register"></input>
            </form>

            {error && 
                <div>
                    <p>{error}</p>
                </div>
            }
        </>
    )
}