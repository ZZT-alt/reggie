package com.zztqvq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zztqvq.common.R;
import com.zztqvq.entity.User;
import com.zztqvq.service.UserService;
import com.zztqvq.util.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 发送验证码
     *
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        log.info("user => " + user);
        if (StringUtils.isNotEmpty(user.getPhone())) {
            //生成随机验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code => " + code);
            session.setAttribute(user.getPhone(), code);
            return R.success("发送成功"+code);
        }
        return R.error("短信发送失败");
    }

    /**
     * 移动端登录
     *
     * @param map
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        String code = map.get("code").toString();
        log.info("code => "+code);
        String phone = map.get("phone").toString();
        Object codeInSession = session.getAttribute(phone);
        if (codeInSession != null && codeInSession.equals(code)) {
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getPhone, phone);
            User one = userService.getOne(wrapper);
            if (one == null) {
                User user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
                session.setAttribute("user",user.getId());
                return R.success(user);
            }
            session.setAttribute("user",one.getId());
            return R.success(one);
        }
        return R.error("登录失败");
    }
}
