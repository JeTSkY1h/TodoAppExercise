import axios from "axios"
import { KanbanCard } from "./models"

const url = "api/kanban/";

let requestConfig = {
    headers: {
        Authorization: `Bearer ${localStorage.getItem('jwt-kanban')}`
    }
}

export const registerUser = (username: string, password: string) => {
    return axios.post("/api/user", {username: username, password: password});
}

export const loginUser = (username: string, password: string) =>{
    return axios.post("/api/login", {username: username, password: password});
}

export const getKanbans = () => {
   return axios.get(url, requestConfig).then(res => res.data);
}

export const putKanban = (task: KanbanCard) => {
    return axios.put(url, task, requestConfig).then(res => res.data);
}

export const postKanban = (task: KanbanCard) => {
    console.log(task);
    return axios.post(url, task, requestConfig).then(res => res.data);
}

export const promoteKanban = (task: KanbanCard) => {
    return axios.put( `${url}next`,task, requestConfig).then(res => res.data);
}

export const demoteKanban = (task: KanbanCard) => {
    return axios.put(`${url}prev`,task, requestConfig).then(res => res.data);
}

export const deleteKanban = (task: KanbanCard) => {
    return axios.delete(url + task, requestConfig).then(res=>res.data);
}

export const getTags = () => {
    return axios.get(`${url}tags`, requestConfig).then(res => res.data);
}

export const getKanbansByTag = (tag: string) => {
    return axios.get(`${url}t/${tag}`, requestConfig).then(res => {
        return res.data
    });
}