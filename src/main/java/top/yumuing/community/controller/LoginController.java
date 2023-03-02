package top.yumuing.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.yumuing.community.entity.User;
import top.yumuing.community.service.UserService;

import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userServiceImpl;


    // 注册页面
    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegisterPage() {
        return "/site/register";
    }

    // 提交注册数据
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(Model model,User user){
       Map<String,Object> map = userServiceImpl.register(user);
       if (map == null || map.isEmpty()){
           model.addAttribute("msg","注册成功！我们已经向你的邮箱发送激活邮件！请尽快激活！");
           model.addAttribute("target","/index");
           return "/site/operate-result";
       }else {
           model.addAttribute("usernameMsg",map.get("usernameMsg"));
           model.addAttribute("passwordMsg",map.get("passwordMsg"));
           model.addAttribute("emailMsg",map.get("emailMsg"));
           return "/site/register";
       }
    }

    // 激活账户

}
