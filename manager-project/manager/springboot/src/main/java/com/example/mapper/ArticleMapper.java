package com.example.mapper;

import com.example.entity.Article;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ArticleMapper {
    /**
     * 新增
     */
    int insert(Article article);


    /**
     * 删除
     */
    int deleteById(Integer articleId);
    /**
     * 修改
     */
    int updateById(Article article);
    /**
     * 查询所有
     */
    List<Article> selectAll(Article article);

    /**
     * 根据ID查询
     */
    Article selectById(Integer articleId);

    /**
     * 查询用户的所有文章
     */
    @Select("select * from article where user_id = #{userId}")
    List<Article> selectUserArticle(Integer userId);


    /**
     *用户点赞的文章
     */
    List<Article> selectLike(Article article);

    /**
     * 用户收藏的文章
     */
    List<Article> selectCollect(Article article);

    /**
     * 用户评论的文章
     */
    List<Article> selectComment(Article article);
}
