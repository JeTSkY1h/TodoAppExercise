import { useState, useEffect } from 'react';
import KanbanColumn from './KanbanColumn';
import KanbanForm from './KanbanForm';
import {getKanbans, getKanbansByTag, getTags} from "../service/apiService"
import { KanbanCard, Tag, TaskStatus } from '../service/models';
import "./App.css"
import { Link, useParams } from 'react-router-dom';
interface Error {
    code: string,
    message: string,
    config: {
        url: string
    }
    response: {
        status: number
    }
}

export default function KanbanBoard() {
    const [err, setErr] = useState<Error>();
    const [kanbans, setKanbans] = useState<Array<KanbanCard>>([])
    const [tags, setTags] = useState<Array<Tag>>([]);
    const {tagID} = useParams();
    
    const fetchTags = ()=> {
        getTags().then(data => {
            setTags(data)
        }).catch(err=>{
            setErr(err);
            console.log(err);   
        });
    }


    const fetchKanbans = () => {
        if(tagID){
            getKanbansByTag(tagID).then(data=>{
                console.log(data);
                setKanbans(data)
            })
                .catch(err=>{
                    setErr(err)
                    console.log(err);
                })
            return
        }
        getKanbans().then(data => {
            setKanbans(data)
        }).catch(err=>{
            setErr(err);
            console.log(err);   
        });
    }

    useEffect(fetchKanbans,[tagID])
    useEffect(fetchTags,[]);

    return (
        <>
            <KanbanForm tags={tags} fetchTags={fetchTags} fetchKanbans={fetchKanbans}/>
            <Link  key={Date.now()*Math.random()} to={`/`}>
                <button onClick={()=>setTimeout(()=>{window.location.reload()},200)} style={{backgroundColor: "#333"}} className="kanban-tag">
                    Filter Löschen
                </button>
            </Link>
                {tags?.map((tag, i) => {
                    return (
                        <Link key={Date.now()*Math.random()} to={`/${tag.tag}`}>
                            <button data-testid={"tagbtn" + i} onClick={()=>setTimeout(()=>{window.location.reload()},200)} style={{backgroundColor: tag.color}} className="kanban-tag">
                                {tag.tag}
                            </button>
                        </Link>
                    )
                })}         
            {err ? err.response.status === 404 ? <h1 data-testid="404">Could not find any Tasks under Tag: {tagID}</h1> : <h1>Could not Reach Backend under: <div>{err.config.url}</div> U sure it's up an running?</h1> :
                <div data-testid='kanban-container' className='column-flex'>
                    <KanbanColumn fetchKanbans={fetchKanbans} title={"Open"} kanbanCrads={kanbans.filter(kanban => kanban.status === TaskStatus.OPEN)}/>
                    <KanbanColumn fetchKanbans={fetchKanbans} title={"In Progress"} kanbanCrads={kanbans.filter(kanban => kanban.status === TaskStatus.IN_PROGRESS)}/>
                    <KanbanColumn fetchKanbans={fetchKanbans} title={"Done"} kanbanCrads={kanbans.filter(kanban => kanban.status === TaskStatus.DONE)}/>
                </div>
            }
        </>

        
    );
}

