package com.example.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private Integer commentId;
    private String content;
    private Integer userId;
    private Integer parentId;
    private Integer rootId;
    private LocalDateTime createTime;
    private Integer articleId;
}
