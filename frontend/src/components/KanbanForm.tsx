import {  useState } from "react"
import { postKanban } from "../service/apiService";
import { KanbanCard, TaskStatus } from "../service/models";
import {Tag} from "../service/models"
import "./KanbanForm.css"

interface kanbanForomProps {
    fetchKanbans: Function,
    tags: Array<Tag>,
    fetchTags: Function,
}

export default function KanbanForm(props: kanbanForomProps){

    const [task, setTask] = useState('');
    const [desc, setDesc] = useState('');
    const [tagString, setTagString] = useState('');

    const genRandomColor = ()=> {
        const letters = "0123456789ADBDEF";
        let color = "#"
        for (let i = 0; i < 6; i++) {
            color += letters[Math.floor(Math.random()*16)]
        }
        return color;
    }

    const setTagsService = ()=>{
        let res: Tag[] = [];
        if(!tagString){return res;}
        tagString.split(',').forEach(tagStr => {
            let foundTag = props.tags.find(tag => tag.tag === tagStr);
            if(foundTag){
                res.push({
                    id: foundTag.id, color: foundTag.color,tag: foundTag.tag
                })
            } else {
                res.push({tag: tagStr, color: genRandomColor()})
            }
        })
        console.log(res);
        return res;
    }

    const handleClick = () => {
        
        postKanban({task: task, description: desc, status: TaskStatus.OPEN, tags: setTagsService()})
        
        .then(()=>{
            props.fetchKanbans();
            props.fetchTags();
        })
        setDesc('')
        setTagString('')
        setTask('')
    }
    const test = ()=>{
        alert('test')
    }
    return (
        <div className='kanban-form'>
                <input value={task} placeholder='Task' onChange={ev => setTask(ev.target.value)}/>
                <input value={desc} placeholder='Description' onChange={ev => setDesc(ev.target.value)}/>
                <input value={tagString} placeholder="Tags" onChange={ev => setTagString(ev.target.value)}/>
                <button onClick={handleClick}>Add Task</button>
        </div>
    )
}