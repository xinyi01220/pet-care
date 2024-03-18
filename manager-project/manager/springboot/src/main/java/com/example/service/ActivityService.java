package com.example.service;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.LevelEnum;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Activity;
import com.example.entity.Department;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.ActivityMapper;
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
 * 社团活动表业务处理
 **/
@Service
public class ActivityService {

    @Resource
    private ActivityMapper activityMapper;
    @Resource
    DepartmentMapper departmentMapper;

    /**
     * 新增
     */
    public void add(Activity activity) {
        activity.setTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
        //获取社团ID
        Account currentUser= TokenUtils.getCurrentUser();
        Department department= departmentMapper.selectByUserId(currentUser.getId());
        activity.setDepartmentId(department.getId());
        activityMapper.insert(activity);

    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        activityMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            activityMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Activity activity) {
        activityMapper.updateById(activity);
    }

    /**
     * 根据ID查询
     */
    public Activity selectById(Integer id) {
        return activityMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Activity> selectAll(Activity activity) {
        return activityMapper.selectAll(activity);
    }

    /**
     * 分页查询
     */
    public PageInfo<Activity> selectPage(Activity activity, Integer pageNum, Integer pageSize) {
        Account currentUser= TokenUtils.getCurrentUser();
        if(RoleEnum.USER.name().equals(currentUser.getRole())){//普通用户,不是管理员
            Department department= departmentMapper.selectByUserId(currentUser.getId());
            if(ObjectUtil.isNotNull(department)){//当前用户是社长
                activity.setDepartmentId(department.getId());//只能查看自己社团的活动
            }
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Activity> list = activityMapper.selectAll(activity);
        return PageInfo.of(list);
    }

}