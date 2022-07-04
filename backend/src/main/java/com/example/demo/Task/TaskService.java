package com.example.demo.Task;

import com.example.demo.User.MyUser;
import com.example.demo.User.MyUserService;
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

    private final MyUserService myUserService;

    public List<Task> getTasksWithTagAndFromUser(String tag, String username){
        MyUser user = myUserService.findByUsername(username).get();
        System.out.println(tagRepo.findByTag(tag).get(0));
        return tagRepo.findByTag(tag).get(0).getTasks().stream().filter(task -> task.getCreatedById().equals(user.getId())).toList();
    }

    public List<Tag> getTags(String username) {
        MyUser user = myUserService.findByUsername(username).get();
        return tagRepo.findByCreatedByID(user.getId());
    }

    public List<Task> getTasksFromUser(String username) {
        MyUser user = myUserService.findByUsername(username).get();
        return taskRepo.findByCreatedById(user.getId());
    }

    public Task saveTask(Task taskToAdd, String username){

        Optional<MyUser> user = myUserService.findByUsername(username);
        taskToAdd.setCreatedById(user.get().getId());

        List<MockTag> addedTags = taskToAdd.getTags().stream().map(mockTag->{
            List<Tag> tagRes = tagRepo.findByTag(mockTag.getTag());
            Tag tag = tagRes.size() <= 0 ?
                    new Tag(mockTag.getTag(), mockTag.getColor()) :
                    tagRes.get(0).getCreatedByID().equals(user.get().getId()) ?
                            tagRes.get(0) :
                            new Tag(mockTag.getTag(), mockTag.getColor());
            tag.setCreatedByID(user.get().getId());
            tag = tagRepo.save(tag);
            System.out.println("SAVED TAG: " + tag);
            mockTag.setId(tag.getId());
            System.out.println("MOCK TAG: " + mockTag);
            return mockTag;
        }).toList();
        
        taskToAdd.setTags(addedTags);
        Task savedTask = taskRepo.save(taskToAdd);
        addedTags.forEach(mockTag->{
            Tag tag = tagRepo.findById(mockTag.getId()).get();
            List<Task> currTasks = tag.getTasks() == null ? new ArrayList<Task>() : tag.getTasks();
            currTasks.add(savedTask);
            tag.setTasks(currTasks);
            tagRepo.save(tag);
        });
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
