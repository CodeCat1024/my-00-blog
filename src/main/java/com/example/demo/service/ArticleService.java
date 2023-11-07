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

    public int getArtCountByUid(Integer uid){
        return articleMapper.getArtCountByUid(uid);
    }

    public List<Articleinfo> getMyList(Integer uid) {
        return articleMapper.getMyList(uid);
    }

    public int del(Integer id, Integer uid) {
        return articleMapper.del(id, uid);
    }

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

    // 修改文章
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
