package com.example.demo.mapper;

import com.example.demo.entity.Userinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    // 注册功能
    public int reg(Userinfo userinfo);

    // 登录功能
    Userinfo getUserByName(@Param("username") String username);

    // 根据用户 id 查询用户信息
    Userinfo getUserById(@Param("id") Integer id);
}
