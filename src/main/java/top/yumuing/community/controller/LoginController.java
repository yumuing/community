package top.yumuing.community.controller;

import com.google.code.kaptcha.Producer;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.yumuing.community.entity.User;
import top.yumuing.community.service.UserService;
import top.yumuing.community.util.CommunityConstant;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;


/**
 * @author yumuing
 */
@Controller
public class LoginController implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userServiceImpl;

    @Autowired
    private Producer imageCodeProducer;


    // 注册页面
    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegisterPage() {
        return "/site/register";
    }

    // 登录页面
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "/site/login";
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
    @RequestMapping(path = "/activation/{userId}/{code}",method = RequestMethod.GET)
    public String activation(Model model, @PathVariable("userId") int userId, @PathVariable("code") String code){
        int result = userServiceImpl.activation(userId,code);
        System.out.println("激活返回结果为："+result);
        if (result == ACTIVATION_NULL){
            model.addAttribute("msg","无效操作！该账号不存在！");
            model.addAttribute("target","/index");
        } else if (result == ACTIVATION_SUCCESS){
            model.addAttribute("msg","激活成功！您的账号已经可以正常使用了！");
            model.addAttribute("target","/login");
        }else if(result == ACTIVATION_REPEAT){
            model.addAttribute("msg","无效操作！该账号已经重复激活了");
            model.addAttribute("target","/index");
        }else {
            model.addAttribute("msg","激活失败！您提供的激活码不正确");
            model.addAttribute("target","/index");
        }
        return "/site/operate-result";
    }

    //验证码
    @RequestMapping(path = "/imageCode",method = RequestMethod.GET)
    public void getImgCode(HttpServletResponse response, HttpSession session){
        String codeText = imageCodeProducer.createText();
        BufferedImage imageCode = imageCodeProducer.createImage(codeText);

        // 将验证码文本存入 session
        session.setAttribute("imageCode", codeText);

        //设置返回类型
        response.setContentType("image/jpeg");

        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(imageCode, "jpeg", os);
        } catch (IOException e) {
            logger.error("响应验证码失败！"+e.getMessage());
        }
    }

    //登录
    @RequestMapping(path = "/login",method = RequestMethod.POST)
    public String login(String username, String password, String code,
                        boolean rememberMe, Model model, HttpSession session, HttpServletResponse response){
        String imageCode = (String) session.getAttribute("imageCode");
        // 验证码
        if (StringUtils.isBlank(imageCode) || StringUtils.isBlank(code) || !imageCode.equalsIgnoreCase(code)){
            model.addAttribute("codeMsg","验证码不正确！");
            return "/site/login";
        }

        // 是否记住我
        int expiredSeconds = rememberMe ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;

        // 检测账号密码
        Map<String,Object> map = userServiceImpl.login(username,password,expiredSeconds);
        if (map.containsKey("loginTicket")){
            //设置cookie
            Cookie cookie = new Cookie("loginTicket",map.get("loginTicket").toString());
            cookie.setPath("/");
            cookie.setMaxAge(expiredSeconds);
            response.addCookie(cookie);
            return "redirect:/index";
        }else {
            model.addAttribute("usernameMsg",map.get("usernameMsg"));
            model.addAttribute("passwordMsg",map.get("passwordMsg"));
            return "/site/login";
        }
    }

    // 退出登录
    @RequestMapping(path = "/logout",method = RequestMethod.GET)
    public String logout(@CookieValue("loginTicket") String loginTicket){
        userServiceImpl.logout(loginTicket);
        return "redirect:/login";
    }
}
