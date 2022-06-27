package com.example.demo.Task;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public
class mockTag {
    
    String id;
    private String tag;
    private String color;

    public mockTag(String tag, String color){
        this.tag = tag;
        this.color = color;
    }

}