package com.example.demo.mapper;

import com.example.demo.entity.Articleinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleMapper {
    // 获取用户的文章数量
    int getArtCountByUid(@Param("uid") Integer uid);

    // 获取文章列表页
    List<Articleinfo> getMyList(@Param("uid") Integer uid);

    // 删除文章
    int del(@Param("id")Integer id, @Param("uid")Integer uid);

    // 查看全文
    Articleinfo getDetail(@Param("id")Integer id);

    // 阅读量 + 1
    int incrementRcount(@Param("id")Integer id);

    // 新增文章
    int add(Articleinfo articleinfo);

    // 更新文章
    int update(Articleinfo articleinfo);

    // 根据页数渲染文章
    List<Articleinfo> getListByPage(@Param("psize")Integer psize, @Param("offsize") Integer offsize);

    // 获取文章总条数
    int getCount();
}
