package top.longmarch.cms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.longmarch.cms.entity.Article;
import top.longmarch.cms.entity.Category;
import top.longmarch.cms.entity.vo.CategoryTree;
import top.longmarch.cms.service.IArticleService;
import top.longmarch.cms.service.ICategoryService;
import top.longmarch.core.annotation.Log;
import top.longmarch.core.common.PageFactory;
import top.longmarch.core.common.Result;
import top.longmarch.core.enums.StatusEnum;
import top.longmarch.core.utils.tree.TreeUtil;
import top.longmarch.sys.entity.Permission;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章 前端控制器
 * </p>
 *
 * @author YuYue
 * @since 2020-01-12
 */
@Api(value = "文章模块", tags = "文章模块接口")
@RestController
@RequestMapping("/cms/article")
public class ArticleController {

    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);
    @Autowired
    private IArticleService articleService;
    @Autowired
    private ICategoryService categoryService;

    @ApiOperation(value = "搜索文章")
    @PostMapping("/search")
    public Result search(@RequestBody(required = false) Map<String, Object> params) {
        IPage<Article> page = PageFactory.getInstance(params);
        Object id = params.get("id");
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        // 自定义查询条件
        wrapper.eq(Objects.nonNull(id), Article::getId, id);
        wrapper.orderByDesc(Article::getId);
        return Result.ok().add(articleService.page(page, wrapper));
    }

    @ApiOperation(value = "查看文章")
    @RequiresPermissions("cms:article:show")
    @GetMapping("/show/{id}")
    public Result show(@PathVariable("id") Long id) {
        List<Long> pIds = new ArrayList<>();
        Article article = articleService.getById(id);
        pIds.add(article.getCategoryId());
        if (article.getCategoryId() > 0) {
            Category category = categoryService.getById(article.getCategoryId());
            if (category != null) {
                getPidList(pIds, category);
                Collections.sort(pIds);
            }
        }
        return Result.ok().add(article).add("pIds", pIds);
    }

    private void getPidList(List<Long> pIds, Category category) {
        if (category.getParentId() == 0) {
            return;
        }
        pIds.add(category.getParentId());
        getPidList(pIds, categoryService.getById(category.getParentId()));
    }

    @Log
    @ApiOperation(value = "创建文章")
    @RequiresPermissions("cms:article:create")
    @PostMapping("/create")
    public Result create(@Validated @RequestBody Article article) {
        log.info("创建文章, 入参：{}", article);
        articleService.saveArticle(article);
        return Result.ok().add(article);
    }

    @Log
    @ApiOperation(value = "更新文章")
    @RequiresPermissions("cms:article:update")
    @PostMapping("/update")
    public Result update(@Validated @RequestBody Article article) {
        log.info("更新文章, 入参：{}", article);
        articleService.updateById(article);
        return Result.ok().add(article);
    }

    @Log
    @ApiOperation(value = "删除文章")
    @RequiresPermissions("cms:article:delete")
    @PostMapping("/delete")
    public Result delete(@RequestBody Long[] ids) {
        log.info("删除文章, ids={}", ids);
        articleService.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }

    @ApiOperation(value = "加载文章分类")
    @GetMapping("/loadCategory")
    public Result loadCategory() {
        List<Category> categoryList = categoryService.list(new LambdaQueryWrapper<Category>().eq(Category::getStatus, StatusEnum.YES.getValue()));
        List<CategoryTree> categoryTreeList = categoryList.stream().map(category -> {
            CategoryTree tree = new CategoryTree(category.getCategoryName());
            tree.setId(category.getId());
            tree.setPid(category.getParentId());
            return tree;
        }).collect(Collectors.toList());
        List<CategoryTree> categoryTree = TreeUtil.list2Tree(categoryTreeList);
        return Result.ok().add(categoryTree);
    }

}
