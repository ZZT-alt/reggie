package com.zztqvq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zztqvq.entity.EmailUser;
import com.zztqvq.mapper.EmailUserMapper;
import com.zztqvq.service.EmailUserService;
import com.zztqvq.util.CodeUtil;
import com.zztqvq.util.MailUtil;
import com.zztqvq.util.SqlSessionFactoryUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailUserServiceImpl extends ServiceImpl<EmailUserMapper, EmailUser> implements EmailUserService {
    @Override
    public void register(EmailUser emailUser) {
//        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
//        SqlSession session = sqlSessionFactory.openSession();
//        EmailUserMapper mapper = session.getMapper(EmailUserMapper.class);
//        mapper.register(emailUser);
        String message = "";
        this.save(emailUser);
        new Thread(new MailUtil(emailUser.getEmail(), emailUser.getCode())).start();
        log.info("插入成功");
    }

    @Override
    public EmailUser findByCode(String code) {
        return null;
    }

    @Override
    public void update(EmailUser emailUser) {

    }


}
