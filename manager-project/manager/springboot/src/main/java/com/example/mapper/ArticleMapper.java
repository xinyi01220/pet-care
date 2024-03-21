package com.example.mapper;

import com.example.entity.Article;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 操作article相关数据接口
 */
public interface ArticleMapper {

    /**
     * 新增
     */
    int insert(Article article);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Article article);

    /**
     * 根据ID查询
     */
    Article selectById(Integer id);

    /**
     * 查询所有
     */
    List<Article> selectAll(Article article);

    @Select("select * from article where user_id = #{userId}")
    List<Article> selectUserArticle(Integer userId);

    @Update("update article set read_count = read_count + 1 where id = #{articleId}")
    void updateReadCount(Integer articleId);

    List<Article> selectLike(Article article);

    List<Article> selectCollect(Article article);

    List<Article> selectComment(Article article);
}