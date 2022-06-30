package com.example.demo.Task;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.demo.Tag.Tag;
import com.example.demo.Tag.TagRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepo taskRepo;
    private final TagRepo tagRepo;

    public List<Task> getTasksWithTag(String tag){
        System.out.println(tagRepo.findByTag(tag).get(0));
        return tagRepo.findByTag(tag).get(0).getTasks(); 
    };

    public List<Tag> getTags() {
        return tagRepo.findAll();
    }

    public Task saveTask(Task taskToAdd){
        List<MockTag> addedTags = taskToAdd.getTags().stream().map(mockTag->{
            List<Tag> tagRes = tagRepo.findByTag(mockTag.getTag());
            Tag tag = tagRes.size() <= 0 ? new Tag(mockTag.getTag(), mockTag.getColor()) : tagRes.get(0);
            tag = tagRepo.save(tag);
            System.out.println("SAVEDTAG:" + tag);
            mockTag.setId(tag.getId());
            return mockTag;
        }).toList();
        taskToAdd.setTags(addedTags);
        Task savedTask = taskRepo.save(taskToAdd);
        addedTags.forEach(mockTag->{
            Tag tag = tagRepo.findById(mockTag.getId()).get();
            List<Task> currTasks = tag.getTasks() == null ? new ArrayList<Task>() : tag.getTasks();
            currTasks.add(savedTask);
            tag.setTasks(currTasks);
            System.out.println("UPDAted_tag: " + tagRepo.save(tag));
        });
        System.out.println("SAVED_TASK:" + savedTask);
        return savedTask;
    }

    public Optional<Task> getTask(String id){
        return taskRepo.findById(id);
    }

    public List<Task> getTasks(){
        return taskRepo.findAll();
    }

    public void promoteTask(Task task){
        
        task.setStatus(task.getStatus().next());
        taskRepo.save(task);

    }

    public void demoteTask(Task task){
        task.setStatus(task.getStatus().prev());
        taskRepo.save(task);

    }

    public void deleteTask(String id){
        Optional<Task> opTaskTodel = getTask(id);
        if(opTaskTodel.isEmpty()) throw new RuntimeException("Der Task den du löschen willst, existiert nicht.");
        Task taskToDel = opTaskTodel.get();
        taskToDel.getTags().forEach(tag->{
            Optional<Tag> DBtag = tagRepo.findById(tag.getId());
            if(DBtag.isPresent()){
                List<Task> currTasks = DBtag.get().getTasks();
                if(currTasks == null){
                    tagRepo.delete(DBtag.get());
                } else {
                    currTasks.remove(taskToDel);
                    if (currTasks.size()<=0) tagRepo.delete(DBtag.get());
                }
            }
        });
        taskRepo.delete(taskToDel);
    }
}
