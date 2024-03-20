package com.example.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Article {
    private Integer articleId;
    private Integer userId;
    private String userName;
    private String title;
    private String content;
    private Integer likeCount;
    private Integer category_id;
    private LocalDateTime createTime;
}
