package com.example.demo.Tag;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepo extends MongoRepository<Tag, String> {
    List<Tag> findByTag(String tag);
    List<Tag>findByCreatedByID(String id);

}
