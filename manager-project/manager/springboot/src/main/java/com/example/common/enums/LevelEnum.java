package com.example.common.enums;

public enum LevelEnum {
    // 社长
    HEADER("社长"),
    STUDENT("学生"),
    ;
    public String level;

    LevelEnum(String level) {
        this.level = level;
    }
}
