package com.example.demo.Task;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface TaskRepo extends MongoRepository<Task, String> {
    public List<Task> findByCreatedById(String id);
}
