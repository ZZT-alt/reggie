package com.zztqvq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zztqvq.entity.EmailUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailUserMapper extends BaseMapper<EmailUser> {
    public void register(@Param("u") EmailUser emailUser);
}
