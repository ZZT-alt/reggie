package com.zztqvq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zztqvq.entity.Dish;
import com.zztqvq.entity.DishFlavor;
import com.zztqvq.mapper.DishFlavorMapper;
import com.zztqvq.service.DishFlavorService;
import com.zztqvq.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
