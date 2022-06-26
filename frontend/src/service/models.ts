export enum TaskStatus {
    OPEN = "OPEN",
    IN_PROGRESS = "IN_PROGRESS",
    DONE = "DONE" 
}

export interface KanbanCard {
    task: string,
    tags?: Tag[], 
    description: string,
    status: TaskStatus,
    id?: string
}

export interface Tag {
    
    id?: string;
    tag: string,
    color: string
    tasks?: KanbanCard[];
}