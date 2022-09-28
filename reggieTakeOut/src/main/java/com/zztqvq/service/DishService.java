package com.zztqvq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zztqvq.dto.DishDto;
import com.zztqvq.entity.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}
