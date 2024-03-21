package com.example.controller;

import com.example.common.Result;
import com.example.entity.Article;
import com.example.service.ArticleService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 博客信息前端操作接口
 **/
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Article article) {
        articleService.add(article);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        articleService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        articleService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Article article) {
        articleService.updateById(article);
        return Result.success();
    }

    @PutMapping("/updateReadCount/{articleId}")
    public Result updateReadCount(@PathVariable Integer articleId) {
        articleService.updateReadCount(articleId);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Article article = articleService.selectById(id);
        return Result.success(article);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Article article) {
        List<Article> list = articleService.selectAll(article);
        return Result.success(list);
    }


    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Article article,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Article> page = articleService.selectPage(article, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 分页查询当前用户的博客列表
     */
    @GetMapping("/selectUser")
    public Result selectUser(Article article,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Article> page = articleService.selectUser(article, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 分页查询当前用户点赞的博客列表
     */
    @GetMapping("/selectLike")
    public Result selectLike(Article article,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Article> page = articleService.selectLike(article, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 分页查询当前用户收藏的博客列表
     */
    @GetMapping("/selectCollect")
    public Result selectCollect(Article article,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Article> page = articleService.selectCollect(article, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 分页查询当前用户评论的博客列表
     */
    @GetMapping("/selectComment")
    public Result selectComment(Article article,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Article> page = articleService.selectComment(article, pageNum, pageSize);
        return Result.success(page);
    }

    /**
     * 博客榜单
     */
    @GetMapping("/selectTop")
    public Result selectTop() {
        List<Article> list = articleService.selectTop();
        return Result.success(list);
    }

    /**
     * 博客推荐
     */
    @GetMapping("/selectRecommend/{articleId}")
    public Result selectRecommend(@PathVariable Integer articleId) {
        Set<Article> articleSet = articleService.selectRecommend(articleId);
        return Result.success(articleSet);
    }

}