import {useEffect, useState} from "react";
import {githubRedirect} from "../service/apiService";
import {useLocation, useNavigate} from "react-router-dom";

export default function GithubLogin(){

    const [error, setError] = useState("");
    let location = useLocation();
    const nav = useNavigate();


    useEffect(()=>{
        githubRedirect(location.search)
            .then(data=>localStorage.setItem("jwt-kanban", data.token))
            .then(()=>nav("/kanban"))
    })

    return(
        <>
            {error ? <div><p>{error}</p></div> :
            <p>Redirecting...</p>
            }
        </>
    )
}