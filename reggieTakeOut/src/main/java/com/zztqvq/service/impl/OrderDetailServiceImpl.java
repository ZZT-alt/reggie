package com.zztqvq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zztqvq.entity.OrderDetail;
import com.zztqvq.mapper.OrderDetailMapper;
import com.zztqvq.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
