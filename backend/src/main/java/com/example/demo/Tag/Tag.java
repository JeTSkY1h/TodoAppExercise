package com.example.demo.Tag;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.example.demo.Task.Task;

import lombok.Data;
@Data
@Document(collection = "Tags")
public class Tag {
    @Id
    private String id;
    private String tag;
    private String color;
    private String createdByID;
    @DBRef
    List<Task> tasks;


    public Tag(String tag, String color){
        this.color = color;
        this.tag = tag;
    }

}