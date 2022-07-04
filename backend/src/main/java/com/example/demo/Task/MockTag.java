package com.example.demo.Task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class MockTag {
    
    String id;
    private String tag;
    private String color;

    public MockTag(String tag, String color){
        this.tag = tag;
        this.color = color;
    }

}