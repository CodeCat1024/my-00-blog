package com.example.demo.service;

import com.example.demo.entity.Articleinfo;
import com.example.demo.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    // 获取用户的文章数量
    public int getArtCountByUid(Integer uid){
        return articleMapper.getArtCountByUid(uid);
    }

    // 获取文章列表页
    public List<Articleinfo> getMyList(Integer uid) {
        return articleMapper.getMyList(uid);
    }

    // 删除文章
    public int del(Integer id, Integer uid) {
        return articleMapper.del(id, uid);
    }

    // 查看全文
    public Articleinfo getDetail(Integer id) {
        return articleMapper.getDetail(id);
    }

    // 阅读量 + 1
    public int incrementRcount(Integer id) {
        return articleMapper.incrementRcount(id);
    }

    // 新增文章
    public int add(Articleinfo articleinfo) {
        return articleMapper.add(articleinfo);
    }

    // 更新文章
    public int update(Articleinfo articleinfo) {
        return articleMapper.update(articleinfo);
    }

    // 根据页数渲染文章
    public List<Articleinfo> getListByPage(Integer psize, Integer offsize) {
        return articleMapper.getListByPage(psize, offsize);
    }

    // 获取文章总条数
    public int getCount() {
        return articleMapper.getCount();
    }
}
