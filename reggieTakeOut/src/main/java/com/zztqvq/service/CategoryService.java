package com.zztqvq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zztqvq.entity.Category;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
