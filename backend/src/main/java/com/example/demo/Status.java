package com.example.demo;

public enum Status {
    OPEN,
    IN_PROGRESS,
    DONE;

    Status next(){
        return this == OPEN ? Status.IN_PROGRESS : Status.DONE;
    }

    Status prev(){
        return this == DONE  ? Status.IN_PROGRESS : Status.OPEN;
    }
}
