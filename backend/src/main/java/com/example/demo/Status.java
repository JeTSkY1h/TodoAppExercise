package com.example.demo;

public enum Status {
    OPEN,
    IN_PROGRESS,
    DONE;

    public Status next(){
        return this == OPEN ? Status.IN_PROGRESS : Status.DONE;
    }

    public Status prev(){
        return this == DONE  ? Status.IN_PROGRESS : Status.OPEN;
    }
}
