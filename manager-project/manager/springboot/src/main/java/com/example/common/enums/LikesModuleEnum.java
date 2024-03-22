package com.example.common.enums;

public enum LikesModuleEnum {
    ARTICLE("文章"),
    ACTIVITY("活动");

    private String value;

    public String getValue() {
        return value;
    }

    LikesModuleEnum(String value) {
        this.value = value;
    }
}
