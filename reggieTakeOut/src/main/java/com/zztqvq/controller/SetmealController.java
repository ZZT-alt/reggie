package com.zztqvq.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zztqvq.common.R;
import com.zztqvq.dto.DishDto;
import com.zztqvq.dto.SetmealDto;
import com.zztqvq.entity.Category;
import com.zztqvq.entity.Dish;
import com.zztqvq.entity.Setmeal;
import com.zztqvq.entity.SetmealDish;
import com.zztqvq.service.CategoryService;
import com.zztqvq.service.DishService;
import com.zztqvq.service.SetmealDishService;
import com.zztqvq.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    SetmealService setmealService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    SetmealDishService setmealDishService;

    @Autowired
    DishService dishService;


    /**
     * 套餐分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<SetmealDto>> page(int page, int pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);
        wrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo, wrapper);
        BeanUtils.copyProperties(pageInfo, dtoPage, "records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> dtoList = new ArrayList<>();
        for (Setmeal record : records) {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(record, setmealDto);
            Long categoryId = record.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                setmealDto.setCategoryName(category.getName());
            }
            dtoList.add(setmealDto);
        }
        dtoPage.setRecords(dtoList);
        return R.success(dtoPage);
    }

    @PostMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("setmealDto => " + setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    @DeleteMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> delete(@RequestParam List<Long> ids) {
        for (Long id : ids) {
            log.info("id => " + id);
        }
        setmealService.removeWithDish(ids);
        return R.success("删除成功");
    }

    @PostMapping("/status/{type}")
    public R<String> updateStatus(@PathVariable int type, Long[] ids) {
        for (Long id : ids) {
            log.info("id => " + id);
        }
        if (type == 1) {
            for (Long id : ids) {
                Setmeal setmeal = setmealService.getById(id);
                setmeal.setStatus(1);
                setmealService.updateById(setmeal);
            }
        }
        else {
            for (Long id : ids) {
                Setmeal setmeal = setmealService.getById(id);
                setmeal.setStatus(0);
                setmealService.updateById(setmeal);
            }
        }
        return R.success("状态更新成功");
    }

    @GetMapping("/list")
    @Cacheable(value = "setmealCache",key = "#categoryId")
    public R<List<Setmeal>> getList(Long categoryId, Integer status) {
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, categoryId);
        setmealLambdaQueryWrapper.eq(Setmeal::getStatus, status);
        List<Setmeal> setmealList = setmealService.list(setmealLambdaQueryWrapper);
        return R.success(setmealList);
    }

//    @GetMapping("/dish/{categoryId}")
//    public R<SetmealDto> getDish(@PathVariable Long categoryId) {
//        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        setmealLambdaQueryWrapper.eq(Setmeal::getId, categoryId);
//        Setmeal setmeal = setmealService.getOne(setmealLambdaQueryWrapper);
//        SetmealDto setmealDto = new SetmealDto();
//        BeanUtils.copyProperties(setmeal,setmealDto);
//        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper=new LambdaQueryWrapper<>();
//        setmealDishLambdaQueryWrapper.eq(SetmealDish::getSetmealId,categoryId);
//        List<SetmealDish> dishes = setmealDishService.list(setmealDishLambdaQueryWrapper);
//        setmealDto.setSetmealDishes(dishes);
//        return R.success(setmealDto);
//    }

    @GetMapping("/dish/{categoryId}")
    public R<List<SetmealDish>> getDish(@PathVariable Long categoryId){
        LambdaQueryWrapper<SetmealDish> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId,categoryId);
        List<SetmealDish> dishes = setmealDishService.list(wrapper);
//        List<Dish> dishList=new ArrayList<>();
//        for (SetmealDish dish : dishes) {
//            LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper<>();
//            dishLambdaQueryWrapper.eq(Dish::getId,dish.getDishId());
//            Dish one = dishService.getOne(dishLambdaQueryWrapper);
//            dishList.add(one);
//        }
        return R.success(dishes);
    }
}
