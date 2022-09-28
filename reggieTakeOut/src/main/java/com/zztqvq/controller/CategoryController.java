package com.zztqvq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zztqvq.common.R;
import com.zztqvq.entity.Category;
import com.zztqvq.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * TODO 分页获取分类
     *
     * @param page     当前页
     * @param pageSize 每页条数
     * @return Success
     */
    @GetMapping("/page")
    public R<Page<Category>> page(int page, int pageSize) {
        log.info("page => {}    pageSize => {}", page, pageSize);
        Page<Category> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo, wrapper);
        return R.success(pageInfo);
    }

    /**
     * TODO 新增分类
     *
     * @param category 分类实体
     * @return Success
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("新增信息 => " + category);
        categoryService.save(category);
        return R.success("Add Success");
    }

    /**
     * TODO 修改分类信息
     *
     * @param category 分类实体
     * @return Success
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return R.success("Update Success");
    }

    /**
     * TODO 通过id删除分类
     *
     * @param id 分类id
     * @return Success
     */
    @DeleteMapping
    public R<String> deleteCategory(Long id) {
        categoryService.remove(id);
//        categoryService.removeById(id);
        return R.success("删除成功");
    }

    @GetMapping("/list")
    public R<List<Category>> getList(Integer type) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(type != null, Category::getType, type);
        List<Category> categoryList = categoryService.list(wrapper);
        return R.success(categoryList);
    }
}
