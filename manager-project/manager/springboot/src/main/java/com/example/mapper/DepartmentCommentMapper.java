package com.example.mapper;

import com.example.entity.DepartmentComment;

import java.util.List;

/**
 * 操作comment相关数据接口
*/
public interface DepartmentCommentMapper {

    /**
      * 新增
    */
    int insert(DepartmentComment departmentComment);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(DepartmentComment departmentComment);

    /**
      * 根据ID查询
    */
    DepartmentComment selectById(Integer id);

    /**
      * 查询所有
    */
    List<DepartmentComment> selectAll(DepartmentComment departmentComment);

}