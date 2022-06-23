import { KanbanCard } from "../service/models"
import KanbanCardComp from "./KanbanCardComp"
import "./KanbanColumn.css"

interface KanbanColumnProps {
    kanbanCrads: KanbanCard[],
    title: string,
    fetchKanbans: Function
}

export default function KanbanColumn(props: KanbanColumnProps){

    let counter = 0

    return (
        <div className="kanban-column">
            <h1 className="kanban-column-title">{props.title}</h1>
            <div className="kanban-column-flex">
                {props.kanbanCrads.map((kanbanCrad, i) => {
                    return <KanbanCardComp index={i} fetchKanban={props.fetchKanbans} key={i} task={kanbanCrad}/> 
                })}    
            </div>
        </div>
    )
}