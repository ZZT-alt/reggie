package com.zztqvq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zztqvq.entity.Orders;

public interface OrdersService extends IService<Orders> {

    public void submit(Orders orders);
}
