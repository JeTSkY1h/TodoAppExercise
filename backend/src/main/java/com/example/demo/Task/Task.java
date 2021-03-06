package com.example.demo.Task;


import java.util.*;

import com.example.demo.User.MyUser;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Tasks")
public class Task {

    @Id
    private String id;
    private  String task;
    private  String description;
    private Status status;
    List<MockTag> tags;

    private String createdById;
    
    public Task(String task, String description, Status status, String id){
        this.task = task;
        this.description = description;
        this.status = status;
        this.id = id;
    }

    public Task(String task, String description, Status status){
        this.task = task;
        this.description = description;
        this.status = status;
    }

    public Task(String task, String description, Status status, List<MockTag> tags){
        this.task = task;
        this.description = description;
        this.status = status;
        this.tags = tags;
    }

    boolean hasTag(String tag){
        return tags.stream().anyMatch(t -> t.getTag() == tag);
    }
}
