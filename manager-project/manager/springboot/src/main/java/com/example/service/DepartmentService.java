package com.example.service;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.LevelEnum;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Department;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.DepartmentMapper;
import com.example.mapper.UserMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 社团信息表业务处理
 **/
@Service
public class DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 新增
     */
    public void add(Department department) {
        if(ObjectUtil.isNotEmpty(department.getUserId())){
            Department dbDepartment=departmentMapper.selectByUserId(department.getUserId());
            if(ObjectUtil.isNotNull(dbDepartment)){//如果已经存在
              throw new  CustomException(ResultCodeEnum.HEADER_ALREADY_ERROR);

            }
        }
        department.setTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
        departmentMapper.insert(department);
//        //学生信息表里的社团id字段更新
//        Department info=departmentMapper.selectByUserId(department.getUserId());
//        User user= userMapper.selectById(department.getUserId());
//        user.setDepartmentId(info.getId());
//        userMapper.updateById(user);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        departmentMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            departmentMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Department department) {
        if(ObjectUtil.isNotEmpty(department.getUserId())){//如果传了userId
            //判断是否已经存在
            Department dbDepartment=departmentMapper.selectByUserId(department.getUserId());
            if(ObjectUtil.isNotEmpty(dbDepartment)){

                if(!dbDepartment.getName().equals(department.getName())){//如果不是同一个社团
                    throw new  CustomException(ResultCodeEnum.HEADER_ALREADY_ERROR);
                }
            }
        }
        departmentMapper.updateById(department);
//        //学生信息表里的社团id字段更新
//        User user= userMapper.selectById(department.getUserId());
//        user.setDepartmentId(department.getId());
//        userMapper.updateById(user);
    }

    /**
     * 根据ID查询
     */
    public Department selectById(Integer id) {
        return departmentMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Department> selectAll(Department department) {
        return departmentMapper.selectAll(department);
    }

    /**
     * 分页查询
     */
    public PageInfo<Department> selectPage(Department department, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            User user = userMapper.selectById(currentUser.getId());
            if (LevelEnum.HEADER.level.equals(user.getLevel())) {
                department.setUserId(user.getId());
            }
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Department> list = departmentMapper.selectAll(department);
        return PageInfo.of(list);
    }

}