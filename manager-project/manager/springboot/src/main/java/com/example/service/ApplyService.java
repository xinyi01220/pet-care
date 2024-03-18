package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.ApplyEnum;
import com.example.common.enums.LevelEnum;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Apply;
import com.example.entity.Department;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.ApplyMapper;
import com.example.mapper.DepartmentMapper;
import com.example.mapper.UserMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 审核信息表业务处理
 **/
@Service
public class ApplyService {

    @Resource
    private ApplyMapper applyMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private DepartmentMapper departmentMapper;

    /**
     * 新增
     */
    public void add(Apply apply) {
        //先去查询还学生是否申请过该社团
        List<Apply> list=applyMapper.selectByStatus(apply.getUserId(),apply.getDepartmentId());
        if(CollectionUtil.isNotEmpty(list)){
            throw new CustomException(ResultCodeEnum.APPLY_ALREADY_ERROR);
        }
        apply.setProcess(ApplyEnum.PROCESS_HEADER_APPLYING.status);
        apply.setStatus(ApplyEnum.STATUS_APPLYING.status);
        applyMapper.insert(apply);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        applyMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            applyMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Apply apply) {
        applyMapper.updateById(apply);
    }

    /**
     * 根据ID查询
     */
    public Apply selectById(Integer id) {
        return applyMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Apply> selectAll(Apply apply) {
        return applyMapper.selectAll(apply);
    }

    /**
     * 分页查询
     */
    public PageInfo<Apply> selectPage(Apply apply, Integer pageNum, Integer pageSize) {
        extracted(apply);
        PageHelper.startPage(pageNum, pageSize);
        List<Apply> list = applyMapper.selectAll(apply);
        return PageInfo.of(list);
    }

    public PageInfo<Apply> selectPage2(Apply apply, Integer pageNum, Integer pageSize) {
        extracted(apply);
        apply.setStatus(ApplyEnum.STATUS_PASS.status);
        PageHelper.startPage(pageNum, pageSize);
        List<Apply> list = applyMapper.selectAll(apply);
        return PageInfo.of(list);
    }

    private void extracted(Apply apply) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            User user = userMapper.selectById(currentUser.getId());
            if (LevelEnum.HEADER.level.equals(user.getLevel())) {
                Department department = departmentMapper.selectByUserId(user.getId());
                if (ObjectUtil.isNotEmpty(department)) {
                    apply.setDepartmentId(department.getId());
                }
            }
        }
    }

    public List<Apply> selectByStatus(Integer userId, Integer departmentId) {
        return applyMapper.selectByStatus(userId, departmentId);
    }

    public List<Apply> selectMyApply(Apply apply) {
        Account currentUser = TokenUtils.getCurrentUser();
        apply.setUserId(currentUser.getId());
        return applyMapper.selectAll(apply);
    }
}