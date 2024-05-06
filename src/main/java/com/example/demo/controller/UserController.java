package com.example.demo.controller;

import com.example.demo.common.AjaxResult;
import com.example.demo.common.AppVariable;
import com.example.demo.common.UserSessionUtil;
import com.example.demo.entity.Userinfo;
import com.example.demo.entity.vo.UserinfoVO;
import com.example.demo.service.ArticleService;
import com.example.demo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    // 登录功能
    @RequestMapping("/login")
    public AjaxResult login(HttpServletRequest request, String username, String password) {
        // 1.非空校验
        if (!StringUtils.hasLength(username) || !StringUtils.hasLength(password))
            return AjaxResult.fail(-1, "非法请求");

        // 2.查询数据库
        Userinfo userinfo = userService.getUserByName(username);
        if (userinfo != null && userinfo.getId() > 0) {
            // 有效的用户，判断密码是否正确（前端传过来的密码跟数据库的密码是否相同）
            if (password.equals(userinfo.getPassword())) {
                // 登录成功
                // 返回给前端之前，隐藏敏感信息
                userinfo.setPassword("");

                // 将用户存储到 session，登录之后持有 Session 之后才能通过拦截器
                HttpSession session = request.getSession(); // 默认为 true
                session.setAttribute(AppVariable.USER_SESSION_KEY, userinfo);

                return AjaxResult.success(userinfo);
            }
        }
        return AjaxResult.success(0, null);
    }

    // 展示：左侧个人信息
    @RequestMapping("/showinfo")
    public AjaxResult showInfo(HttpServletRequest request) {
        UserinfoVO userinfoVO = new UserinfoVO();
        // 得到当前登录用户（从 session 中获取）
        Userinfo userinfo = UserSessionUtil.getUser(request);
        if (userinfo == null) {
            return AjaxResult.fail(-1, "非法请求");
        }

        // Spring 提供的深克隆方法，将 userinfo 中相同的字段复制到 userinfoVO
        BeanUtils.copyProperties(userinfo, userinfoVO);

        // 得到用户发表文章的总数
        userinfoVO.setArtCount(articleService.getArtCountByUid(userinfo.getId()));

        return AjaxResult.success(userinfoVO);
    }

    // 退出登录
    @RequestMapping("/logout")
    public AjaxResult logout(HttpSession session) {
        session.removeAttribute(AppVariable.USER_SESSION_KEY);
        return AjaxResult.success(1);
    }

    // 根据用户 id 查询用户信息
    @RequestMapping("/getuserbyid")
    public AjaxResult getUserById(Integer id) {
        if (id == null || id <= 0){
            // 无效参数
            return AjaxResult.fail(-1, "非法参数");
        }
        Userinfo userinfo = userService.getUserById(id);
        if (userinfo == null || userinfo.getId() <= 0) {
            return AjaxResult.fail(-1, "非法参数");
        }
        // 去除 userinfo 中的敏感信息，如密码
        userinfo.setPassword("");
        UserinfoVO userinfoVO = new UserinfoVO();
        BeanUtils.copyProperties(userinfo, userinfoVO);

        // 查询当前用户发表的文章数
        userinfoVO.setArtCount(articleService.getArtCountByUid(id));
        return AjaxResult.success(userinfoVO);
    }

}















