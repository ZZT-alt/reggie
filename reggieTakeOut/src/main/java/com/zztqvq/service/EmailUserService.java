package com.zztqvq.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zztqvq.entity.EmailUser;

public interface EmailUserService extends IService<EmailUser> {
    //注册用户操作，将用户信息存入数据库中
    public void register(EmailUser emailUser);

    //查询激活码，返回用户名
    public EmailUser findByCode(String code);

    //查询到激活码后，更新数据库中的表
    public void update(EmailUser emailUser);
}
