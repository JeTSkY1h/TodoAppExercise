package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepo taskRepo;

    List<Task> getTasksWithTag(String tag){
        List<Task> res = taskRepo.getTasksByTag(tag);
        if(res.size() <=0) throw new RuntimeException("Keine Tasks mit dem Tag: " + tag + " gefunden.");
        return res; 
    };

    List<Tag> getTags() {
        return taskRepo.getTags();
    }

    Task saveTask(Task taskToAdd){
        if(taskToAdd.getTask().equals("")){
            throw new RuntimeException("Leerer Task Name!");
        }
        return taskRepo.createOrEdit(taskToAdd);
    }

    Task getTask(String id){
        return taskRepo.getTask(id);
    }

    List<Task> getTasks(){
        return taskRepo.getTaskList();
    }

    void promoteTask(Task task){
        
        task.setStatus(task.getStatus().next());
        
        taskRepo.createOrEdit(task);

    }

    void demoteTask(Task task){
        
        task.setStatus(task.getStatus().prev());
        
        taskRepo.createOrEdit(task);

    }

    void deleteTask(String id){
        taskRepo.deleteTask(id);
    }
}
