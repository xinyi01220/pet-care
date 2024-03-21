package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.DepartmentComment;
import com.example.entity.Department;
import com.example.mapper.DepartmentCommentMapper;
import com.example.mapper.DepartmentMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 评论信息表业务处理
 **/
@Service
public class DepartmentCommentService {

    @Resource
    private DepartmentCommentMapper departmentCommentMapper;
    @Resource
    private DepartmentMapper departmentMapper;

    /**
     * 新增
     */
    public void add(DepartmentComment departmentComment) {
        departmentComment.setTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        departmentCommentMapper.insert(departmentComment);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        departmentCommentMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            departmentCommentMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(DepartmentComment departmentComment) {
        departmentCommentMapper.updateById(departmentComment);
    }

    /**
     * 根据ID查询
     */
    public DepartmentComment selectById(Integer id) {
        return departmentCommentMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<DepartmentComment> selectAll(DepartmentComment departmentComment) {
        //先查出所有父结点
        departmentComment.setParentId(0);
        List<DepartmentComment> allParents= departmentCommentMapper.selectAll(departmentComment);
        for (DepartmentComment parent : allParents) {
            //查询出每个父结点的所有子结点
            departmentComment.setParentId(parent.getId());
            List<DepartmentComment> children= departmentCommentMapper.selectAll(departmentComment);
            parent.setChildren(children);
        }
        return allParents;
    }

    /**
     * 分页查询
     */
    public PageInfo<DepartmentComment> selectPage(DepartmentComment departmentComment, Integer pageNum, Integer pageSize) {
        //获取当前用户
        Account currentUser= TokenUtils.getCurrentUser();
        if(RoleEnum.USER.name().equals(currentUser.getRole())){
            Department department= departmentMapper.selectByUserId(currentUser.getId());
            if(ObjectUtil.isNotNull(department)){
                departmentComment.setDepartmentId(department.getId());
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        List<DepartmentComment> list = departmentCommentMapper.selectAll(departmentComment);
        return PageInfo.of(list);
    }

}