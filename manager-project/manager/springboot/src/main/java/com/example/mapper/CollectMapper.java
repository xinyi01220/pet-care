package com.example.mapper;

import com.example.entity.Collect;
import org.apache.ibatis.annotations.Param;

public interface CollectMapper {
    void insert(Collect collect);

    Collect selectUserCollect(Collect collect);

    void deleteById(Integer id);

    int selectByArticleId(@Param("articleId") Integer articleId);
}
