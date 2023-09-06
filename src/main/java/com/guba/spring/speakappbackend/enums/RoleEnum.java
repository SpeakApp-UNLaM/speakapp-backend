package com.guba.spring.speakappbackend.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum RoleEnum {
    PROFESSIONAL("PROFESSIONAL"),
    PATIENT("PATIENT"),
    ADMIN("ADMIN");

    private static final Map<String, RoleEnum> ROLE_BY_NAME = Arrays
            .stream(RoleEnum.values())
            .collect(Collectors.toMap(RoleEnum::getName, Function.identity()));

    private final String name;

    RoleEnum(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static RoleEnum getRole(String name) {
        return ROLE_BY_NAME.get(name);
    }

    @Override
    public String toString() {
        return name;
    }

}
