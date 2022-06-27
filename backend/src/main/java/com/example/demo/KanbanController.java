package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Tag.Tag;
import com.example.demo.Task.Task;
import com.example.demo.Task.TaskService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class KanbanController {
    private final TaskService taskService;
    
    @PostMapping("/kanban")
    ResponseEntity<Task> addTask(@RequestBody Task task){
        try {
            System.out.println("TASK:" + task);
            return  ResponseEntity.of(Optional.of(taskService.saveTask(task)));
        } catch(RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        } 

    }

    @GetMapping("/kanban/t/{tag}")
    ResponseEntity<List<Task>>getTasksWithTag(@PathVariable String tag){
        try {
            return ResponseEntity.of(Optional.of(taskService.getTasksWithTag(tag)));
        } catch(RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
        
    }

    @GetMapping("/kanban/tags")
        List<Tag> getTags(){
            return taskService.getTags();
        }
    
    @GetMapping("/kanban")
    List<Task> getTasks(){
        return taskService.getTasks();
    }

    @PutMapping("/kanban/next")
    void promoteTask(@RequestBody Task task){
        taskService.promoteTask(task);
    }

    @PutMapping("/kanban/prev")
    void demoteTask(@RequestBody Task task){
        taskService.demoteTask(task);
    }

    @DeleteMapping("/kanban/{id}")
    void deleteTask(@PathVariable String id){
        taskService.deleteTask(id);
    }

    @GetMapping("/kanban/{id}")
    ResponseEntity<Task> getTask(@PathVariable String id){
        return ResponseEntity.of(taskService.getTask(id));
    }

    @PutMapping("/kanban")
    Task editTask(@RequestBody Task task){
        return taskService.saveTask(task);
    }
}