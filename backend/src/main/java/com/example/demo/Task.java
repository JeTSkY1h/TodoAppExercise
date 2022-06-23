package com.example.demo;


import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private  List<Tag> tags;
    private  String task;
    private  String description;
    private Status status;
    private String id = UUID.randomUUID().toString();

    Task(String task, String description, Status status, String id){
        this.task = task;
        this.description = description;
        this.status = status;
        this.id = id;
    }

    Task(String task, String description, Status status){
        this.task = task;
        this.description = description;
        this.status = status;
    }

    Task(String task, String description, Status status, List<Tag> tags){
        this.task = task;
        this.description = description;
        this.status = status;
        this.tags = tags;
    }

    boolean hasTag(String tag){
        return tags.stream().anyMatch(t -> t.getTag() == tag);
    }
}
