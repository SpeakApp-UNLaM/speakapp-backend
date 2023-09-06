package com.guba.spring.speakappbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum TaskStatus {
    CREATED("created"),
    PENDING("pending"),
    PROGRESSING("progressing"),
    DONE("done");

    private static final Map<String, TaskStatus> TASK_STATUS_BY_NAME = Arrays
            .stream(TaskStatus.values())
            .collect(Collectors.toMap(TaskStatus::getName, Function.identity()));

    private final String name;

    TaskStatus(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static TaskStatus getTaskStatus(String name) {
        return TASK_STATUS_BY_NAME.get(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
