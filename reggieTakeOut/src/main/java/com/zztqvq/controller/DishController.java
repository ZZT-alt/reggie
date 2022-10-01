package com.zztqvq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zztqvq.common.R;
import com.zztqvq.dto.DishDto;
import com.zztqvq.entity.Category;
import com.zztqvq.entity.Dish;
import com.zztqvq.entity.DishFlavor;
import com.zztqvq.service.CategoryService;
import com.zztqvq.service.DishFlavorService;
import com.zztqvq.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CacheManager cacheManager;

    /**
     * TODO 分页查询所以菜品
     *
     * @param page     当前页
     * @param pageSize 每页数据条数
     * @param name
     * @return Success
     */
    @GetMapping("/page")
    public R<Page<DishDto>> page(int page, int pageSize, String name) {
        log.info("page => {}    pageSize => {}    name => {}", page, pageSize, name);

        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Dish::getUpdateTime);
        wrapper.eq(Dish::getIsDeleted, 0);
        wrapper.like(StringUtils.isNotEmpty(name), Dish::getName, name);
        dishService.page(pageInfo, wrapper);
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> dishDtoList = new ArrayList<>();
        for (Dish record : records) {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(record, dishDto);
            Long categoryId = record.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            dishDtoList.add(dishDto);
        }
        dishDtoPage.setRecords(dishDtoList);
        return R.success(dishDtoPage);
    }

    @GetMapping("/list")
    public R<List<DishDto>> getList(Long categoryId, String name, Integer status) {
        //先从redis中获取数据
        List<DishDto> dishDtos;
        String key = "dish" + "_" + categoryId + "_" + status;
        dishDtos = (List<DishDto>) redisTemplate.opsForValue().get(key);
        if (dishDtos != null) {
            return R.success(dishDtos);
        }
        else {
            dishDtos = new ArrayList<>();
        }
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Dish::getStatus, 1);
        if (name == null) {
            wrapper.eq(Dish::getCategoryId, categoryId);
            List<Dish> dishList = dishService.list(wrapper);
            for (Dish dish : dishList) {
                DishDto dishDto = new DishDto();
                BeanUtils.copyProperties(dish, dishDto);
                Long dishId = dish.getId();
                LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
                dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);
                List<DishFlavor> dishFlavorList = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
                dishDto.setFlavors(dishFlavorList);
                dishDtos.add(dishDto);
            }
        }
        else {
            wrapper.like(StringUtils.isNotEmpty(name), Dish::getName, name);
            List<Dish> dishList = dishService.list(wrapper);
            for (Dish dish : dishList) {
                DishDto dishDto = new DishDto();
                BeanUtils.copyProperties(dish, dishDto);
                dishDtos.add(dishDto);
            }
        }
        redisTemplate.opsForValue().set(key, dishDtos, 1, TimeUnit.HOURS);
        return R.success(dishDtos);
    }
//    @GetMapping("/list")
//    public R<List<Dish>> getList(Long categoryId, String name) {
//        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Dish::getStatus, 1);
//        if (name == null) {
//            wrapper.eq(Dish::getCategoryId, categoryId);
//            List<Dish> dishList = dishService.list(wrapper);
//            return R.success(dishList);
//        }
//        else {
//            wrapper.like(StringUtils.isNotEmpty(name), Dish::getName, name);
//            List<Dish> dishList = dishService.list(wrapper);
//            return R.success(dishList);
//        }
//    }


    /**
     * TODO 新增菜品
     *
     * @param dishDto
     * @return Success
     */
    @CachePut(value = "dishCache",key = "#dishDto.id")
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("保存成功");
    }

    /**
     * TODO 修改页面，回写数据
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> getSingle(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdFlavor(id);
        return R.success(dishDto);
    }

    /**
     * TODO 修改菜品信息
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavor(dishDto);
        String key = "dish" + "_" + dishDto.getCategoryId() + "_" + dishDto.getStatus();
        redisTemplate.delete(key);
        return R.success("更新成功");
    }

    @PostMapping("/status/{type}")
    public R<String> updateStatus(@PathVariable int type, Long[] ids) {
        log.info("type => " + type);
        log.info("ids => ");
        for (Long id : ids) {
            log.info("id => " + id);
            Dish dish = dishService.getById(id);
            if (type == 1) {
                dish.setStatus(1);
            }
            else {
                dish.setStatus(0);
            }
            dishService.updateById(dish);
        }
        return R.success("状态更新成功");
    }

    @DeleteMapping
    public R<String> deleteByIds(@RequestParam Long[] ids) {
        for (Long id : ids) {
            Dish dish = dishService.getById(id);
            dish.setIsDeleted(1);
            dishService.updateById(dish);
        }
        return R.success("删除成功");
    }
}
