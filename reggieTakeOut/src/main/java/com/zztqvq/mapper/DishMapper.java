package com.zztqvq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zztqvq.common.R;
import com.zztqvq.dto.DishDto;
import com.zztqvq.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
    //public List<DishDto> selectPage(@Param("page") int page, @Param("pageSize") int pageSize, @Param("name") String name);

    public List<DishDto> selectByPage(@Param("page") int page, @Param("pageSize") int pageSize, @Param("name") String name);
}
