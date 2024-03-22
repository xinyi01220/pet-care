package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.example.common.enums.LikesModuleEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.*;
import com.example.mapper.ArticleMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 博客信息业务处理
 **/
@Service
public class ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    UserService userService;

    @Resource
    LikesService likesService;

    @Resource
    CollectService collectService;

    /**
     * 新增
     */
    public void add(Article article) {
        article.setDate(DateUtil.today());
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            article.setUserId(currentUser.getId());
        }
        articleMapper.insert(article);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        articleMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            articleMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Article article) {
        articleMapper.updateById(article);
    }

    /**
     * 根据ID查询
     */
    public Article selectById(Integer id) {
        Article article = articleMapper.selectById(id);
        User user = userService.selectById(article.getUserId());
        List<Article> userArticleList = articleMapper.selectUserArticle(user.getId());
        user.setArticleCount(userArticleList.size());
        //  当前用户收到的点赞和收藏的数据
        int userLikesCount = 0;
        int userCollectCount = 0;
        for (Article b : userArticleList) {
            Integer fid = b.getId();
            int likesCount = likesService.selectByFidAndModule(fid, LikesModuleEnum.ARTICLE.getValue());
            userLikesCount += likesCount;

            int collectCount = collectService.selectByFidAndModule(fid, LikesModuleEnum.ARTICLE.getValue());
            userCollectCount += collectCount;
        }
        user.setLikesCount(userLikesCount);
        user.setCollectCount(userCollectCount);


        article.setUser(user);  // 设置作者信息
        // 查询当前文章的点赞数据
        int likesCount = likesService.selectByFidAndModule(id, LikesModuleEnum.ARTICLE.getValue());
        article.setLikesCount(likesCount);
        Likes userLikes = likesService.selectUserLikes(id, LikesModuleEnum.ARTICLE.getValue());
        article.setUserLike(userLikes != null);

        // 查询当文章的收藏数据
        int collectCount = collectService.selectByFidAndModule(id, LikesModuleEnum.ARTICLE.getValue());
        article.setCollectCount(collectCount);
        Collect userCollect = collectService.selectUserCollect(id, LikesModuleEnum.ARTICLE.getValue());
        article.setUserCollect(userCollect != null);

        return article;
    }

    /**
     * 查询所有
     */
    public List<Article> selectAll(Article article) {
        return articleMapper.selectAll(article);
    }

    /**
     * 分页查询
     */
    public PageInfo<Article> selectPage(Article article, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> list = articleMapper.selectAll(article);
        for (Article b : list) {
            int likesCount = likesService.selectByFidAndModule(b.getId(), LikesModuleEnum.ARTICLE.getValue());
            b.setLikesCount(likesCount);
        }
        return PageInfo.of(list);
    }

    public List<Article> selectTop() {
        List<Article> articleList = this.selectAll(null);
        articleList = articleList.stream().sorted((b1, b2) -> b2.getReadCount().compareTo(b1.getReadCount()))
                .limit(20)
                .collect(Collectors.toList());
        return articleList;
    }


    public Set<Article> selectRecommend(Integer articleId) {
        Article article = this.selectById(articleId);
        String tags = article.getTags();
        Set<Article> articleSet = new HashSet<>();
        if (ObjectUtil.isNotEmpty(tags)) {
            List<Article> articleList = this.selectAll(null);
            JSONArray tagsArr = JSONUtil.parseArray(tags);
            for (Object tag : tagsArr) {
                // 筛选出包含当前博客标签的其他的博客列表
                Set<Article> collect = articleList.stream().filter(b -> b.getTags().contains(tag.toString()) && !articleId.equals(b.getId()))
                        .collect(Collectors.toSet());
                articleSet.addAll(collect);
            }
        }
        articleSet = articleSet.stream().limit(5).collect(Collectors.toSet());
        articleSet.forEach(b -> {
            int likesCount = likesService.selectByFidAndModule(b.getId(), LikesModuleEnum.ARTICLE.getValue());
            b.setLikesCount(likesCount);
        });
        return articleSet;
    }

    public void updateReadCount(Integer articleId) {
        articleMapper.updateReadCount(articleId);
    }

    public PageInfo<Article> selectUser(Article article, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            article.setUserId(currentUser.getId());
        }
        return this.selectPage(article, pageNum, pageSize);
    }

    // 查询用户点赞的数据
    public PageInfo<Article> selectLike(Article article, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            article.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Article> list = articleMapper.selectLike(article);
        PageInfo<Article> pageInfo = PageInfo.of(list);
        List<Article> articleList = pageInfo.getList();
        for (Article b : articleList) {
            int likesCount = likesService.selectByFidAndModule(b.getId(), LikesModuleEnum.ARTICLE.getValue());
            b.setLikesCount(likesCount);
        }
        return pageInfo;
    }

    public PageInfo<Article> selectCollect(Article article, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            article.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Article> list = articleMapper.selectCollect(article);
        PageInfo<Article> pageInfo = PageInfo.of(list);
        List<Article> articleList = pageInfo.getList();
        for (Article b : articleList) {
            int likesCount = likesService.selectByFidAndModule(b.getId(), LikesModuleEnum.ARTICLE.getValue());
            b.setLikesCount(likesCount);
        }
        return pageInfo;
    }

    public PageInfo<Article> selectComment(Article article, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            article.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Article> list = articleMapper.selectComment(article);
        PageInfo<Article> pageInfo = PageInfo.of(list);
        List<Article> articleList = pageInfo.getList();
        for (Article b : articleList) {
            int likesCount = likesService.selectByFidAndModule(b.getId(), LikesModuleEnum.ARTICLE.getValue());
            b.setLikesCount(likesCount);
        }
        return pageInfo;
    }
}