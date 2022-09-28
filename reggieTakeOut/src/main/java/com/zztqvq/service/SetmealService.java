package com.zztqvq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zztqvq.dto.SetmealDto;
import com.zztqvq.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);

    public void removeWithDish(List<Long> ids);
}
