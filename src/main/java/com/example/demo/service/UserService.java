package com.example.demo.service;

import com.example.demo.entity.Userinfo;
import com.example.demo.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    // 注册功能
    public int reg(Userinfo userinfo) {
        return userMapper.reg(userinfo);
    }

    // 登录功能
    public Userinfo getUserByName (String username) {
        return userMapper.getUserByName(username);
    }

    // 根据用户 id 获取用户信息
    public Userinfo getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

}
