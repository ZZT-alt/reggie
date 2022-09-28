package com.zztqvq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zztqvq.common.CustomException;
import com.zztqvq.entity.Category;
import com.zztqvq.entity.Dish;
import com.zztqvq.entity.Setmeal;
import com.zztqvq.mapper.CategoryMapper;
import com.zztqvq.service.CategoryService;
import com.zztqvq.service.DishService;
import com.zztqvq.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除，删除前需进行判断
     *
     * @param id 待删除的分类id
     */
    @Override
    public void remove(Long id) {
        //查询当前分类是否关联了菜品，如果已关联，抛出业务异常
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int dishCount = dishService.count(dishLambdaQueryWrapper);
        if (dishCount > 0) {
            //已关联菜品，抛出异常
            throw new CustomException("当前分类已关联菜品，无法删除");
        }

        //查询当前分类是否关联了套餐，如果已关联，抛出业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int setmealCount = setmealService.count(setmealLambdaQueryWrapper);
        if (setmealCount > 0) {
            //已关联套餐，抛出异常
            throw new CustomException("当前分类已关联套餐，无法删除");
        }
        //正常删除分类
        super.removeById(id);
    }
}
