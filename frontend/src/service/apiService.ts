import axios from "axios"
import { KanbanCard } from "./models"

const url = "http://localhost:8080/api/kanban";

export const getKanbans = () => {
   return axios.get(url).then(res => res.data);
}

export const putKanban = (task: KanbanCard) => {
    return axios.post(url, task).then(res => res.data);
}

export const postKanban = (task: KanbanCard) => {
    return axios.post(url, task).then(res => res.data);
}

export const promoteKanban = (task: KanbanCard) => {
    return axios.put( `${url}/next`,task).then(res => res.data);
}

export const demoteKanban = (task: KanbanCard) => {
    return axios.put(`${url}/prev`,task).then(res => res.data);
}

export const deleteKanban = (task: KanbanCard) => {
    return axios.delete(`${url}/${task.id}`).then(res=>res.data);
}

export const getTags = () => {
    return axios.get(`${url}/tags`).then(res => res.data);
}

export const getKanbansByTag = (tag: string) => {
    return axios.get(`${url}/t/${tag}`).then(res => {
        return res.data
    });
}