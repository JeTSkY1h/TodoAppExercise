package com.example.demo;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TaskRepo {

    private final Map< String, Task > taskList = new HashMap<>();

    List<Tag>getTags(){
        Set<Tag> tags = new HashSet<Tag>();
        for (Task task : taskList.values()) {
            for (Tag tag : task.getTags()) {
                tags.add(tag);
            }
        }
        return new ArrayList<Tag>(tags);
    }

    List<Task>getTasksByTag(String tag){
        // ArrayList<Task> res = new ArrayList<Task>();
        // for(Task task: taskList.values()){
        //     for(Tag currTag: task.getTags()){
        //         if(currTag.getTag().equals(tag)){
        //             res.add(task);
        //         }
        //     }
        // }
        // if(res.size()<=0) throw new RuntimeException("Unter dem Tag: " + tag + " konnten keine Tasks gefunden werden");
        // return res;
        return taskList.values().stream().filter(task -> task.getTags().stream().anyMatch(t -> t.getTag().equals(tag))).toList();
    }

    Task getTask(String id){
        return taskList.get(id);
    }

    Task createOrEdit(Task task){
        taskList.put(task.getId(), task);
        return task;
    }

    void deleteTask(String id){
        taskList.remove(id);
    }

    List<Task> getTaskList(){
        return taskList.values().stream().toList();
    }

}
