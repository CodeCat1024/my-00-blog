package com.example.demo.controller;

import com.example.demo.common.AjaxResult;
import com.example.demo.common.UserSessionUtil;
import com.example.demo.entity.Articleinfo;
import com.example.demo.entity.Userinfo;
import com.example.demo.service.ArticleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/art")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // 展示：右侧列表信息
    @RequestMapping("/mylist")
    public AjaxResult getMyList(HttpServletRequest request) {
        Userinfo userinfo = UserSessionUtil.getUser(request);
        if (userinfo == null) {
            return AjaxResult.fail(-1, "非法请求");
        }
        List<Articleinfo> list = articleService.getMyList(userinfo.getId()) ;
        return AjaxResult.success(list);
    }

    // 删除文章
    @RequestMapping("/del")
    public AjaxResult del(HttpServletRequest request, Integer id) {
        if (id == null || id <= 0) {
            // 参数有误
            return AjaxResult.fail(-1, "参数异常");
        }
        Userinfo user = UserSessionUtil.getUser(request);
        if (user == null) {
            return AjaxResult.fail(-2, "用户未登录");
        }
        return AjaxResult.success(articleService.del(id, user.getId()));
    }

    // 显示文章详情
    @RequestMapping("/detail")
    public AjaxResult getDetail(Integer id) {
        if (id == null || id <= 0)
            return AjaxResult.fail(-1, "非法参数");
        return AjaxResult.success(articleService.getDetail(id));
    }

    // 阅读量 + 1
    @RequestMapping("updatercount")
    public AjaxResult incrementRCount(Integer id) {
        if (id != null && id > 0) {
            return AjaxResult.success(articleService.incrementRcount(id));
        }
        return AjaxResult.fail(-1, "未知错误");
    }

    // 新增文章
    @RequestMapping("/add")
    public AjaxResult add(HttpServletRequest request, Articleinfo articleinfo) {
        if (articleinfo == null || !StringUtils.hasLength(articleinfo.getTitle()) ||
        !StringUtils.hasLength(articleinfo.getContent()))
        {
            return AjaxResult.fail(-1, "非法参数");
        }
        // 数据库添加操作
        // 得到当前登录用户的 uid
        Userinfo userinfo = UserSessionUtil.getUser(request);
        if (userinfo == null || userinfo.getId() <= 0) {
            // 无效的登录用户
            return AjaxResult.fail(-2, "无效的登录用户");
        }
        articleinfo.setUid(userinfo.getId());
        // 添加到数据库并返回结果
        return AjaxResult.success(articleService.add(articleinfo));
    }

    // 修改文章
    @RequestMapping("/update")
    public AjaxResult update(HttpServletRequest request, Articleinfo articleinfo) {
        // 非空校验
        if (articleinfo == null || !StringUtils.hasLength(articleinfo.getTitle()) || !StringUtils.hasLength(articleinfo.getContent()) || articleinfo.getId() == null) {
            // 非法参数
            return AjaxResult.fail(-1, "非法参数");
        }

        // 得到当前登录用户的 id
        Userinfo userinfo = UserSessionUtil.getUser(request);
        if (userinfo == null && userinfo.getId() == null) {
            // 无效用户
            return AjaxResult.fail(-2, "无效用户");
        }

        // 核心代码：必须让后端去验证用户，不能让前端传用户 id，否则有伪造风险
        articleinfo.setUid(userinfo.getId());
        articleinfo.setUpdatetime(LocalDateTime.now());
        return AjaxResult.success(articleService.update(articleinfo));
    }

    /**
     * 根据页数渲染文章
     * @param pindex：当前页码，从1开始
     * @param psize：每页显示条数
     * @return
     */
    @RequestMapping("/listbypage")
    public AjaxResult getListByPage(Integer pindex, Integer psize) {
        // 1. 参数校正
        if (pindex == null && pindex <= 1) {
            pindex = 1;
        }
        if (psize == null || psize <= 1) {
            psize = 2;
        }
        // 分页公式的值 = （当前页码 - 1） + 每页显示条数
        int offset = (pindex - 1) * psize;
        // 文章列表数据
        List<Articleinfo> list = articleService.getListByPage(psize, offset);
        // 当前列表总共有多少页
        // a.总共有多少条数据
        int totalCount = articleService.getCount();
        // b.总条数 / psize （每页显示条数）
        double pcountdb = totalCount * 1.0 / psize * 1.0;
        // c.使用进 1 法得到总页数
        int pcount = (int)Math.ceil(pcountdb);
        HashMap<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("pcount", pcount);
        return AjaxResult.success(result);
    }
































}
