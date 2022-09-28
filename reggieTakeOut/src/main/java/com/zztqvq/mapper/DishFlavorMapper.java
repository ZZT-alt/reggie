package com.zztqvq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zztqvq.entity.Dish;
import com.zztqvq.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
}
