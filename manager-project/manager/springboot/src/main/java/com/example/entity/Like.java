package com.example.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Like {
    private Integer likeId;
    private Integer userId;
    private Integer articleId;
    private LocalDateTime createTime;
}
