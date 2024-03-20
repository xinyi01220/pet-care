package com.example.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Collect {
    private Integer collectId;
    private Integer userId;
    private Integer articleId;
    private LocalDateTime createTime;
}
