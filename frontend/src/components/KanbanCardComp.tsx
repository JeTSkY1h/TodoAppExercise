import {KanbanCard, TaskStatus} from "../service/models";
import { promoteKanban, demoteKanban, deleteKanban, putKanban } from "../service/apiService";
import "./KanbanCardComp.css"
import { useEffect, useState } from "react";
import {FaPen, FaTimes, FaChevronLeft, FaChevronRight, FaTrash} from "react-icons/fa"


interface KanbanCardCompProps {
    index: number,
    task: KanbanCard,
    fetchKanban: Function
}

export default function KanbanCardComp(props: KanbanCardCompProps) {
    
    const [editMode, setEditMode] = useState(false);
    const [task, setTask] = useState(props.task.task);
    const [desc, setDesc] = useState(props.task.description)

    useEffect(()=>{
       
        
    },[task, desc])

    const saveKanban = ()=>{
        putKanban({
            tags: props.task.tags,
            task: task,
            description: desc,
            status: props.task.status,
            id: props.task.id
        }).then(()=>props.fetchKanban())
    }

    const promote = () => {
        promoteKanban(props.task).then(()=>props.fetchKanban())
        
    }
    
    const demote = () => {
        demoteKanban(props.task)
        .then(()=>props.fetchKanban());
        
    }

    const delKanban = () => {
        deleteKanban(props.task)
        .then(()=>props.fetchKanban())
        
    }

    console.log("kanban" + props.index);
    

    return (
        <div data-testid={"kanban" + props.index } className={`kanban-card ${editMode && "edit-mode"}`}>
            <button className="kanban-edit-btn kanban-btn" onClick={()=>{
                setEditMode(editMode => !editMode)
                saveKanban();
            }}>{editMode ? <FaTimes/> : <FaPen/>}</button>
            {editMode ? 
            <> 
                <div className="edit-inputs">
                    <input onChange={(ev)=>{setTask(ev.target.value)}} value={task} className="edit-mode kanban-edit-task"></input>
                    <input onChange={(ev)=>{setDesc(ev.target.value)}} value={desc} className="edit-mode kanban-edit-desc"></input>
                </div>
            </> : 


            <>
                <div className="kanban-card-info">
                    <div className="kanban-card-tags">
                        {props.task.tags?.map(tag => <span key={Date.now()*Math.random()} style={{backgroundColor: tag.color}} className="kanban-tag">{tag.tag}</span>)}
                    </div>
                    <p className="kanban-card-title">{props.task.task}</p>
                    <p className="kanban-card-desc">{props.task.description}</p>
                </div>
            </>
            }
            <div className="btns">
            {props.task.status !== TaskStatus.OPEN ? <button className="kanban-btn" onClick={demote}><FaChevronLeft size={16}/></button> : <button className="kanban-btn" onClick={delKanban}><FaTrash size={16}/></button>}
            {props.task.status !== TaskStatus.DONE ? <button className="kanban-btn" onClick={promote}><FaChevronRight size={16}/></button> : <button className="kanban-btn" onClick={delKanban}><FaTrash size={16}/></button>}
            </div>

            
        </div>
    )
}

