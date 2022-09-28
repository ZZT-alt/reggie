package com.zztqvq.controller;


import com.zztqvq.common.R;
import com.zztqvq.entity.Orders;
import com.zztqvq.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    OrdersService ordersService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        log.info("orders => " + orders);
        ordersService.submit(orders);
        return R.success("下单成功");
    }
}
