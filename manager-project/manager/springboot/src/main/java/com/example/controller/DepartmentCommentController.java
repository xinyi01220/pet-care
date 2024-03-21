package com.example.controller;

import com.example.common.Result;
import com.example.entity.DepartmentComment;
import com.example.service.DepartmentCommentService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 评论信息表前端操作接口
 **/
@RestController
@RequestMapping("/departmentComment")
public class DepartmentCommentController {

    @Resource
    private DepartmentCommentService departmentCommentService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody DepartmentComment departmentComment) {
        departmentCommentService.add(departmentComment);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        departmentCommentService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        departmentCommentService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody DepartmentComment departmentComment) {
        departmentCommentService.updateById(departmentComment);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        DepartmentComment departmentComment = departmentCommentService.selectById(id);
        return Result.success(departmentComment);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(DepartmentComment departmentComment) {
        List<DepartmentComment> list = departmentCommentService.selectAll(departmentComment);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(DepartmentComment departmentComment,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<DepartmentComment> page = departmentCommentService.selectPage(departmentComment, pageNum, pageSize);
        return Result.success(page);
    }

}