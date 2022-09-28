package com.zztqvq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zztqvq.common.R;
import com.zztqvq.entity.EmailUser;
import com.zztqvq.service.EmailUserService;
import com.zztqvq.util.CodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/emailUser")
public class EmailUserController {

    @Autowired
    private EmailUserService emailUserService;

    @PostMapping("/register")
    public R<String> register(String username, String password, String email) {
        log.info("username => {}    password => {}    email =>{}", username, password, email);
        EmailUser emailUser = new EmailUser();
        emailUser.setEmail(email);
        emailUser.setStatus(0);
        emailUser.setUsername(username);
        emailUser.setPassword(password);
        emailUser.setCode(CodeUtil.generateUniqueCode());
        emailUserService.register(emailUser);
        return R.success("注册成功，请去邮箱激活账号");
    }

    @GetMapping("/save")
    @Transactional
    public R<String> save(String code) {
        log.info("code => " + code);
        LambdaQueryWrapper<EmailUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EmailUser::getCode, code);
        wrapper.eq(EmailUser::getStatus, 0);
        EmailUser one = emailUserService.getOne(wrapper);
        if (one != null) {
            one.setStatus(1);
            one.setCode(null);
            emailUserService.updateById(one);
            log.info("激活成功");
            return R.success("激活成功");
        }
        else {
            return R.error("账号已激活");
        }
    }
}
