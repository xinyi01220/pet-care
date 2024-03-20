package com.example.mapper;

import com.example.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentMapper {
    int insert(Comment comment);
    int deleteById(Integer id);
    int updateById(Comment comment);
    Comment selectById(Integer id);
    List<Comment> selectAll(Comment comment);

    /**
     * 查询前台展示的评论信息(没太懂这个功能)
     */
    List<Comment> selectForUser(Comment comment);

    @Select("select count(*) from comment where article_id = #{articleId}")
    Integer selectCount(@Param("articleId") Integer articleId);
}
