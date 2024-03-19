package com.example.service;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Information;
import com.example.entity.Department;
import com.example.mapper.InformationMapper;
import com.example.mapper.DepartmentMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 社团咨询业务处理
 **/
@Service
public class InformationService {

    @Resource
    private InformationMapper informationMapper;
    @Resource
    DepartmentMapper departmentMapper;

    /**
     * 新增
     */
    public void add(Information information) {
        information.setTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
        //获取社团ID
        Account currentUser= TokenUtils.getCurrentUser();
        Department department= departmentMapper.selectByUserId(currentUser.getId());
        information.setDepartmentId(department.getId());
        informationMapper.insert(information);

    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        informationMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            informationMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Information information) {
        informationMapper.updateById(information);
    }

    /**
     * 根据ID查询
     */
    public Information selectById(Integer id) {
        Information information= informationMapper.selectById(id);
        Department department=departmentMapper.selectById(information.getDepartmentId());
        if(ObjectUtil.isNotNull(department)){
            information.setDepartmentName(department.getName());
        }
        return information;
    }

    /**
     * 查询所有
     */
    public List<Information> selectAll(Information information) {
        List<Information> activities=informationMapper.selectAll(information);
        for (Information dbInformation:activities){
            dbInformation.setDescription(dbInformation.getDescription().replaceAll("<p>","").replaceAll("</p>",""));
        }
        return activities;
    }

    /**
     * 分页查询
     */
    public PageInfo<Information> selectPage(Information information, Integer pageNum, Integer pageSize) {
        Account currentUser= TokenUtils.getCurrentUser();
        if(RoleEnum.USER.name().equals(currentUser.getRole())){//普通用户,不是管理员
            Department department= departmentMapper.selectByUserId(currentUser.getId());
            if(ObjectUtil.isNotNull(department)){//当前用户是社长
                information.setDepartmentId(department.getId());//只能查看自己社团的活动
            }
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Information> list = informationMapper.selectAll(information);
        return PageInfo.of(list);
    }
    public PageInfo<Information> selectPage2(Information information, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Information> list = selectAll(information);
        return PageInfo.of(list);
    }

}