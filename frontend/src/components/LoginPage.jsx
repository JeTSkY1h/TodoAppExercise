import { useState } from "react"
import { loginUser } from "../service/apiService";
import { Link } from "react-router-dom";

export default function LoginPage(){
    const [password, setPassword] = useState("");    
    const [username, setUsername] = useState("");
    const [error, setError] = useState("");

    const submitForm = (e) => {
        e.preventDefault();
        loginUser(username,password ).then(res => res.data).then(data=>localStorage.setItem("jwt-kanban", data))
            .catch(
                ()=>setError("Da ist was schiefgelaufen.")
            )
    }

    return (
        <>
            <form className="LoginForm" onSubmit={submitForm}>
                <input value={username} onChange={(e)=>setUsername(e.target.value)}></input>
                <input value={password} onChange={(e)=>setPassword(e.target.value)}></input>
                <input type="submit"></input>
                <p>Dont have a Account?</p> <Link to="/register">register</Link>
            </form>
            {error &&
                <div className="login-error">
                    <p>{error}</p>
                </div>
            }
            
        </>
    )
}