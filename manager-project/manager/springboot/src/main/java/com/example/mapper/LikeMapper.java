package com.example.mapper;

import com.example.entity.Like;
import org.apache.ibatis.annotations.Param;

public interface LikeMapper {
    void insert (Like like);

    /**
     * 查询用户点赞列表
     */
    Like selectUserLike(Like like);

    void deleteById(Integer like_id);

    /**
     * 查询文章点赞数
     */
    int selectByArticleId(@Param("articleId") Integer articleId);
}
