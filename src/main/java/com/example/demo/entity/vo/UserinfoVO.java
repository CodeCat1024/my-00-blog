package com.example.demo.entity.vo;

import com.example.demo.entity.Userinfo;
import lombok.Data;

@Data
public class UserinfoVO extends Userinfo {
    private Integer artCount; // 此人发表的文章总数
}
