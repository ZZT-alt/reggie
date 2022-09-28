package com.zztqvq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zztqvq.entity.User;
import com.zztqvq.mapper.UserMapper;
import com.zztqvq.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
