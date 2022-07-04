package com.example.demo;

import java.security.Principal;
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
    ResponseEntity<Task> addTask(@RequestBody Task task, Principal principal){
        String username = principal.getName();
        try {
            return  ResponseEntity.of(Optional.of(taskService.saveTask(task, username)));
        } catch(RuntimeException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        } 

    }

    @GetMapping("/kanban/t/{tag}")
    ResponseEntity<List<Task>>getTasksWithTag(@PathVariable String tag, Principal principal){
        String username = principal.getName();
        try {
            return ResponseEntity.of(Optional.of(taskService.getTasksWithTagAndFromUser(tag, username)));
        } catch(RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
        
    }

    @GetMapping("/kanban/tags")
        List<Tag> getTags(Principal principal){
            String username = principal.getName();
            return taskService.getTags(username);
        }
    
    @GetMapping("/kanban")
    List<Task> getTasks(Principal principal){
        String username = principal.getName();
        return taskService.getTasksFromUser(username);
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
    Task editTask(@RequestBody Task task, Principal principal){
        String username = principal.getName();
        return taskService.saveTask(task, username);
    }
}