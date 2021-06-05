package com.nclg.controller;



import com.github.pagehelper.StringUtil;
import com.nclg.bean.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/user")
public class UserController{
    @RequestMapping(value = "/login")
       public ModelAndView login(User user){
        // 表面校验
        if(!StringUtil.isEmpty(user.getName()) || !StringUtil.isEmpty(user.getPassword())){
            return new ModelAndView("login")
                    .addObject("message", "账号或密码不能为空")
                    .addObject("failuser", user);
        }
        // 获取主体
        Subject subject = SecurityUtils.getSubject();
        try{
            // 调用安全认证框架的登录方法
            subject.login(new UsernamePasswordToken(user.getName(), user.getPassword()));
        }catch(AuthenticationException ex){
            System.out.println("登陆失败: " + ex.getMessage());
            return new ModelAndView("login")
                    .addObject("message", "用户不存在")
                    .addObject("failuser", user);
        }
        // 登录成功后重定向到首页
        return new ModelAndView("redirect:/index");
    }
}
