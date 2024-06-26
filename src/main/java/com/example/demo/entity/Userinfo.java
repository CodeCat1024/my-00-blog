package com.example.demo.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Userinfo {
    private Integer id;
    private String username;
    private String password;
    private String photo;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer state;
}
